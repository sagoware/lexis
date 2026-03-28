package com.lexis.gui;

import com.lexis.util.AltManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import java.util.Optional;
import java.util.UUID;

public class AddAccountScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget nameField;

    public AddAccountScreen(Screen parent) {
        super(Text.of("Add Account"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int x = this.width / 2 - 100;

        // İsim yazma kutusu
        this.nameField = new TextFieldWidget(this.textRenderer, x, 80, 200, 20, Text.of("Username"));
        this.addSelectableChild(nameField);

        // Offline Ekle Butonu
        this.addDrawableChild(ButtonWidget.builder(Text.of("Add Offline"), b -> {
            if (!nameField.getText().isEmpty()) {
                String name = nameField.getText();
                UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
                Session s = new Session(name, uuid, "0", Optional.empty(), Optional.empty(), Session.AccountType.MOJANG);
                AltManager.accounts.add(s);
                this.client.setScreen(parent); // Listeye geri dön
            }
        }).dimensions(x, 110, 200, 20).build());

        // İptal Butonu
        this.addDrawableChild(ButtonWidget.builder(Text.of("Cancel"), b -> this.client.setScreen(parent))
                .dimensions(x, 140, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, "Enter Offline Nickname", this.width / 2, 60, 0xFFFFFF);
        this.nameField.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }
}