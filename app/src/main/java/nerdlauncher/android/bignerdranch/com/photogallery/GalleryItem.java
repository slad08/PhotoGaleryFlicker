package nerdlauncher.android.bignerdranch.com.photogallery;

/**
 * Created by Denis on 15.10.2015.
 */
public class GalleryItem {
    private String mCaption;
    private String mId;
    private String mUrl;
    private String mOwner;

    public String toString(){
        return mCaption;
    }

    public String getCaption() {
        return mCaption;
    }


    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }
    public String getPhotoPageUrl(){
        return "http://www.flickr.com/photos/"+mOwner+"/"+mId;
    }
    public void setCaption(String mCaption) {
        this.mCaption = mCaption;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
