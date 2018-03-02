package com.discovr.discord.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.Gson

@Entity
class Card {
    // TODO check nulls
    @PrimaryKey(autoGenerate = true) var id: Int? = null
    @ColumnInfo var quantity: Int? = null
    @ColumnInfo var title: String? = null
    @ColumnInfo var description: String? = null
    @ColumnInfo var quote: String? = null
    @ColumnInfo var timer: Int? = null
    @ColumnInfo var dices: List<String>? = null
    @ColumnInfo var tags: List<String>? = null

    companion object {
        const val FILE_NAME = "cards"
        const val FILE_DIRECTORY = "raw"

        fun builder(): Builder {
            return Builder()
        }
    }

    /**
     * Constructors
     */

    constructor()

    private constructor(id: Int?, quantity: Int?, title: String?, description: String?, quote: String?,
                        timer: Int?, dice: List<String>?, tags: List<String>?) {
        this.id = id
        this.quantity = quantity
        this.title = title
        this.description = description
        this.quote = quote
        this.timer = timer
        this.dices = dice
        this.tags = tags
    }

    /**
     * Builder
     */
    private constructor(builder: Builder) {
        this.id = builder.id
        this.quantity = builder.quantity
        this.title = builder.title
        this.description = builder.description
        this.quote = builder.quote
        this.timer = builder.timer
        this.dices = builder.dices
        this.tags = builder.tags
    }

    class Builder {
        internal var id: Int? = null
        internal var quantity: Int? = null
        internal var title: String? = null
        internal var description: String? = null
        internal var quote: String? = null
        internal var timer: Int? = null
        internal var dices: List<String>? = null
        internal var tags: List<String>? = null

        fun id(`val`: Int?): Builder {
            id = `val`
            return this
        }

        fun quantity(`val`: Int?): Builder {
            quantity = `val`
            return this
        }

        fun title(`val`: String?): Builder {
            title = `val`
            return this
        }

        fun description(`val`: String?): Builder {
            description = `val`
            return this
        }

        fun quote(`val`: String?): Builder {
            quote = `val`
            return this
        }

        fun dices(`val`: List<String>?): Builder {
            dices = `val`
            return this
        }

        fun timer(`val`: Int?): Builder {
            timer = `val`
            return this
        }

        fun tags(`val`: List<String>?): Builder {
            tags = `val`
            return this
        }

        fun build(): Card {
            return Card(this)
        }
    }

    /**
     * Common methods
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val card = other as Card?

        if (if (id != null) id != card!!.id else card!!.id != null) return false
        if (if (quantity != null) quantity != card.quantity else card.quantity != null)
            return false
        if (if (title != null) title != card.title else card.title != null) return false
        if (if (description != null) description != card.description else card.description != null)
            return false
        if (if (quote != null) quote != card.quote else card.quote != null) return false
        if (if (timer != null) timer != card.timer else card.timer != null) return false
        if (if (dices != null) dices != card.dices else card.dices != null) return false
        return if (tags != null) tags == card.tags else card.tags == null
    }

    override fun hashCode(): Int {
        var result = if (id != null) id!!.hashCode() else 0
        result = 31 * result + if (quantity != null) quantity!!.hashCode() else 0
        result = 31 * result + if (title != null) title!!.hashCode() else 0
        result = 31 * result + if (description != null) description!!.hashCode() else 0
        result = 31 * result + if (quote != null) quote!!.hashCode() else 0
        result = 31 * result + if (timer != null) timer!!.hashCode() else 0
        result = 31 * result + if (dices != null) dices!!.hashCode() else 0
        result = 31 * result + if (tags != null) tags!!.hashCode() else 0
        return result
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}