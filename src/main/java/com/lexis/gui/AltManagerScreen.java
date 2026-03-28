package com.lexis.gui;

import com.lexis.mixin.MinecraftClientAccessor;
import com.lexis.util.AltManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import java.util.Optional;
import java.util.UUID;

public class AltManagerScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget nameField;
    private Session selectedAlt = null;
    private boolean addingAccount = false;

    public AltManagerScreen(Screen parent) {
        super(Text.of("Alt Manager"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        // HATA BURADAYDI: btnH eklenmemişti
        int btnW = 100;
        int btnH = 20;
        int startX = this.width / 2 - 155;
        int startY = this.height - 40;

        // Hesap ekleme kutusu
        this.nameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Text.of("Nickname"));
        this.addSelectableChild(nameField);

        // --- ALT BUTONLAR ---

        // Login Butonu
        this.addDrawableChild(ButtonWidget.builder(Text.of("Login"), b -> {
            if (selectedAlt != null) {
                ((MinecraftClientAccessor) client).setSession(selectedAlt);
                this.client.setScreen(parent);
            }
        }).dimensions(startX, startY, btnW, btnH).build());

        // Add Account Butonu (Kutuyu açar)
        this.addDrawableChild(ButtonWidget.builder(Text.of("Add Account"), b -> {
            addingAccount = !addingAccount;
            nameField.setFocused(addingAccount);
        }).dimensions(startX + 105, startY, btnW, btnH).build());

        // Cancel Butonu
        this.addDrawableChild(ButtonWidget.builder(Text.of("Cancel"), b -> this.client.setScreen(parent))
                .dimensions(startX + 210, startY, btnW, btnH).build());

        // Pop-up içindeki gerçek "Add" Butonu
        this.addDrawableChild(ButtonWidget.builder(Text.of("Add"), b -> {
            if (addingAccount && !nameField.getText().isEmpty()) {
                String name = nameField.getText();
                UUID uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes());
                Session s = new Session(name, uuid, "0", Optional.empty(), Optional.empty(), Session.AccountType.MOJANG);
                AltManager.accounts.add(s);
                nameField.setText("");
                addingAccount = false;
            }
        }).dimensions(this.width / 2 + 105, this.height / 2 - 10, 40, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // Arka planı çiz
        this.renderBackground(context, mouseX, mouseY, delta);

        // Liste Kutusu
        int listX = 50;
        int listW = this.width - 100;
        int listY = 30;
        int listH = this.height - 80;
        context.fill(listX, listY, listX + listW, listY + listH, 0x80000000);

        // Hesapları Listele
        int y = listY + 5;
        for (Session s : AltManager.accounts) {
            boolean isHovered = mouseX >= listX && mouseX <= listX + listW && mouseY >= y && mouseY <= y + 12;
            int color = (s == selectedAlt) ? 0x00FF00 : 0xFFFFFF;

            if (isHovered || s == selectedAlt) {
                context.fill(listX + 2, y, listX + listW - 2, y + 12, 0x40FFFFFF);
            }
            context.drawTextWithShadow(this.textRenderer, s.getUsername(), listX + 5, y + 2, color);
            y += 14;
        }

        // Add Account Pop-up Tasarımı
        if (addingAccount) {
            context.fill(0, 0, this.width, this.height, 0xCC000000);
            context.drawCenteredTextWithShadow(this.textRenderer, "Enter Offline Name:", this.width / 2, this.height / 2 - 25, 0xFFFFFF);
            nameField.render(context, mouseX, mouseY, delta);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (addingAccount) return super.mouseClicked(mouseX, mouseY, button);

        int listX = 50;
        int y = 35;
        for (Session s : AltManager.accounts) {
            if (mouseX >= listX && mouseX <= this.width - 50 && mouseY >= y && mouseY <= y + 14) {
                selectedAlt = s;
                return true;
            }
            y += 14;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}