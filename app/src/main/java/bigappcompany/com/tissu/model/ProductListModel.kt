package bigappcompany.com.vegan.model

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Samuel Robert <sam></sam>@spotsoon.com>
 * @created on 29 Jun 2017 at 3:22 PM
 */

class ProductListModel : Parcelable {
    protected constructor(`in`: Parcel) {
        id_products = `in`.readString()
        product_name = `in`.readString()
        id_category = `in`.readString()
        name_category = `in`.readString()
        price_with_tax = `in`.readString()
        products_image = `in`.readString()
        prod_short_desc = `in`.readString()
        prod_desc = `in`.readString()
        user_selected_qty = `in`.readString()
        addingtext = `in`.readString()
    }

    var id_products: String? = null
        private set
    var product_name: String? = null
        private set
    var id_category: String? = null
        private set
    var name_category: String? = null
        private set
    var price_with_tax: String? = null
        private set
    var products_image: String? = null
        private set
    var prod_short_desc: String? = null
        private set
    var prod_desc: String? = null
        private set

    var addingtext: String? = null
            set
    var user_selected_qty: String? = null
    set(value) {
        field = value
    }

    constructor(id_products: String, product_name: String, id_category: String, name_category: String,
                price_with_tax: String, products_image: String, prod_short_desc: String,
                prod_desc: String, user_selected_qty: String, addingtext: String) {
        this.id_products = id_products
        this.product_name = product_name
        this.id_category = id_category
        this.name_category = name_category
        this.price_with_tax = price_with_tax
        this.products_image = products_image
        this.prod_short_desc = prod_short_desc
        this.prod_desc = prod_desc
        this.user_selected_qty = user_selected_qty
        this.addingtext = addingtext
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeString(id_products)
        dest.writeString(product_name)
        dest.writeString(id_category)
        dest.writeString(name_category)
        dest.writeString(price_with_tax)
        dest.writeString(products_image)
        dest.writeString(prod_short_desc)
        dest.writeString(prod_desc)
        dest.writeString(user_selected_qty)
        dest.writeString(addingtext)
    }

    companion object {

        val CREATOR: Parcelable.Creator<ProductListModel> = object : Parcelable.Creator<ProductListModel> {
            override fun createFromParcel(`in`: Parcel): ProductListModel {
                return ProductListModel(`in`)
            }

            override fun newArray(size: Int): Array<ProductListModel?> {
                return arrayOfNulls(size)
            }
        }
    }

}
