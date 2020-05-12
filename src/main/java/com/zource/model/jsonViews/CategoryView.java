/*
 * Copyright (c) 2019.
 * Author: Mikhail Sigachev
 */

package com.zource.model.jsonViews;


//JSON Views
public class CategoryView {

    public static class Short {
    } // id + name + etc

    public static class Children extends Short {
    } // + children categories

    public static class Parents extends Short {
    }

    public static class Full extends Children {
    }


}
