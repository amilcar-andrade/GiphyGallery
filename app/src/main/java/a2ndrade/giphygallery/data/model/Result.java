package a2ndrade.giphygallery.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model class representing data returned from Giphy
 */
public class Result implements Parcelable {

    /*
    {
    "data" : [...],
    "meta" : {
      "status" : 200,
      "msg" : "OK"
      },
    "pagination" : {
      "count" : 25,
      "offset" : 0
      }
    }
     */
    public final Meta meta;
    @SerializedName(value="data")
    public final List<Data> data;


    protected Result(Parcel in) {
        meta = in.readParcelable(Meta.class.getClassLoader());
        data = in.createTypedArrayList(Data.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(meta, flags);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}

