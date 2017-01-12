package com.dm47021.cyanbadgeprovider.impl;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import com.dm47021.cyanbadgeprovider.ShortcutBadgeException;
import com.dm47021.cyanbadgeprovider.ShortcutBadger;
import com.dm47021.cyanbadgeprovider.util.ImageUtil;

import java.util.Arrays;
import java.util.List;

/**
 * @author Leo Lin / Dm47021
 */

public class CyanogenmodTrebuchetBadger extends ShortcutBadger {
    private static final String CONTENT_URI = "content://com.android.launcher3.settings/favorites?notify=true";

    public CyanogenmodTrebuchetBadger(Context context) {
        super(context);
    }

    @Override
    protected void executeBadge(int badgeCount) throws ShortcutBadgeException {
        byte[] bytes = ImageUtil.drawBadgeOnAppIcon(mContext, badgeCount);
        String appName = mContext.getResources().getText(mContext.getResources().getIdentifier("app_name",
                "string", getContextPackageName())).toString();

        Uri mUri = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = mContext.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("iconType", 1);
        contentValues.put("itemType", 1);
        contentValues.put("icon", bytes);
        contentResolver.update(mUri, contentValues, "title=?", new String[]{appName});
    }

    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList(
                "com.android.launcher3",
                "com.google.android.googlequicksearchbox"
        );
    }
}
