package com.lexis.util;

import net.minecraft.client.session.Session;
import java.util.ArrayList;
import java.util.List;

public class AltManager {
    public static final List<Session> accounts = new ArrayList<>();
    public static Session selectedAccount = null;

    public static void addAccount(Session session) {
        // Eğer aynı isimde hesap varsa listeden çıkar, güncelini ekle
        accounts.removeIf(s -> s.getUsername().equalsIgnoreCase(session.getUsername()));
        accounts.add(session);
        selectedAccount = session;
    }
}