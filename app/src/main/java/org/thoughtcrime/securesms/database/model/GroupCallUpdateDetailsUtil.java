package org.thoughtcrime.securesms.database.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.signal.core.util.logging.Log;
import org.thoughtcrime.securesms.database.model.databaseprotos.GroupCallUpdateDetails;
import org.thoughtcrime.securesms.util.Base64;

import java.io.IOException;
import java.util.List;

public final class GroupCallUpdateDetailsUtil {

  private static final String TAG = Log.tag(GroupCallUpdateDetailsUtil.class);

  private GroupCallUpdateDetailsUtil() {
  }

  public static @NonNull GroupCallUpdateDetails parse(@Nullable String body) {
    GroupCallUpdateDetails groupCallUpdateDetails = new GroupCallUpdateDetails();

    if (body == null) {
      return groupCallUpdateDetails;
    }

    try {
      groupCallUpdateDetails = GroupCallUpdateDetails.ADAPTER.decode(Base64.decode(body));
    } catch (IOException e) {
      Log.w(TAG, "Group call update details could not be read", e);
    }

    return groupCallUpdateDetails;
  }

  public static @NonNull String createUpdatedBody(@NonNull GroupCallUpdateDetails groupCallUpdateDetails, @NonNull List<String> inCallUuids, boolean isCallFull) {
    GroupCallUpdateDetails.Builder builder = groupCallUpdateDetails.newBuilder()
                                                                   .isCallFull(isCallFull)
                                                                   .inCallUuids(inCallUuids);

    return Base64.encodeBytes(builder.build().encode());
  }
}
