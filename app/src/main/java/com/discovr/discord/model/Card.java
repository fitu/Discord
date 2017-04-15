package com.discovr.discord.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

@Entity
public class Card implements Parcelable {
    public static final String FILE_NAME = "cards";
    public static final String FILE_DIRECTORY = "raw";
    // TODO this?
    public static final int RULES_CARD_ID = 0;

    @PrimaryKey(autoGenerate = true) private Integer id;
    @ColumnInfo private Integer quantity;
    @ColumnInfo private String title;
    @ColumnInfo private String description;
    @ColumnInfo private String quote;
    @ColumnInfo private Integer timer;
    @ColumnInfo private List<String> dices;
    @ColumnInfo private List<String> tags;

    /**
     * Constructors
     */
    public Card() {
    }

    private Card(Integer id, Integer quantity, String title, String description, String quote,
                 List<String> dice, Integer timer, List<String> tags) {
        this.id = id;
        this.quantity = quantity;
        this.title = title;
        this.description = description;
        this.quote = quote;
        this.dices = dice;
        this.timer = timer;
        this.tags = tags;
    }

    private Card(Builder builder) {
        this.id = builder.id;
        this.quantity = builder.quantity;
        this.title = builder.title;
        this.description = builder.description;
        this.quote = builder.quote;
        this.dices = builder.dices;
        this.timer = builder.timer;
        this.tags = builder.tags;
    }

    public static Card copyCard(Card card) {
        return builder()
                .id(card.getId())
                .quantity(card.getQuantity())
                .title(card.getTitle())
                .description(card.getDescription())
                .quote(card.getQuote())
                .dices(card.getDices())
                .timer(card.getTimer())
                .tags(card.getTags())
                .build();
    }

    /**
     * Builder
     */
    public static final class Builder {
        private Integer id;
        private Integer quantity;
        private String title;
        private String description;
        private String quote;
        private List<String> dices;
        private Integer timer;
        private List<String> tags;

        public Builder() {
        }

        public Builder id(Integer val) {
            id = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder quote(String val) {
            quote = val;
            return this;
        }

        public Builder dices(List<String> val) {
            dices = val;
            return this;
        }

        public Builder timer(Integer val) {
            timer = val;
            return this;
        }

        public Builder tags(List<String> val) {
            tags = val;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }

    public static Builder builder() {
        return new Builder()
                .dices(Collections.emptyList())
                .tags(Collections.emptyList());
    }

    /**
     * Parcelable
     **/
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.quantity);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.quote);
        dest.writeStringList(this.dices);
        dest.writeInt(this.timer);
        dest.writeStringList(this.tags);
    }

    protected Card(Parcel in) {
        this.id = in.readInt();
        this.quantity = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.quote = in.readString();
        this.dices = in.createStringArrayList();
        this.timer = in.readInt();
        this.tags = in.createStringArrayList();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel source) {
            return new Card(source);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    /**
     * Getters and Setters
     */
    public Integer getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getQuote() {
        return quote;
    }

    public List<String> getDices() {
        return dices;
    }

    public Integer getTimer() {
        return timer;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setTimer(Integer timer) {
        this.timer = timer;
    }

    public void setDices(List<String> dices) {
        this.dices = dices;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Common methods
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != null ? !id.equals(card.id) : card.id != null) return false;
        if (quantity != null ? !quantity.equals(card.quantity) : card.quantity != null)
            return false;
        if (title != null ? !title.equals(card.title) : card.title != null) return false;
        if (description != null ? !description.equals(card.description) : card.description != null)
            return false;
        if (quote != null ? !quote.equals(card.quote) : card.quote != null) return false;
        if (timer != null ? !timer.equals(card.timer) : card.timer != null) return false;
        if (dices != null ? !dices.equals(card.dices) : card.dices != null) return false;
        return tags != null ? tags.equals(card.tags) : card.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (quote != null ? quote.hashCode() : 0);
        result = 31 * result + (timer != null ? timer.hashCode() : 0);
        result = 31 * result + (dices != null ? dices.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}