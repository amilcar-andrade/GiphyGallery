package a2ndrade.giphygallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Meta implements Parcelable {

    public final int status;
    public final String msg;

    protected Meta(Parcel in) {
        status = in.readInt();
        msg = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeString(msg);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Meta> CREATOR = new Creator<Meta>() {
        @Override
        public Meta createFromParcel(Parcel in) {
            return new Meta(in);
        }

        @Override
        public Meta[] newArray(int size) {
            return new Meta[size];
        }
    };
}
