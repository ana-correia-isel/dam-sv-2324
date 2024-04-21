# Tutorial 4

?> Review 1.1 - Add Pokemon List <br>
Review 1.2 - Correct name in menu pokemon_regions, 


> Goals
> Build a full aplication using Clean Architecture with MVVM

# App - Pokedex

## View Models and Data Binding 

**ViewModels** offer a number of benefits:

1. ViewModel‘s are lifecycle-aware, which means they know when the attached Activity/Fragment is destroyed and can immediately release data observers and other
resources.
2. They survive configuration changes, so if your data is observed or fetched through
a ViewModel, it’s still available after your Activity or Fragment is re-created. This
means you can re-use the data without fetching it again.
3. ViewModel takes the responsibility of holding and managing data. It acts as a bridge
between your Repository and the View. Freeing up your Activity or Fragment from
managing data allows you to write more concise and unit-testable code.

**Data Binding** is a feature provided by Android Jetpack that allows you to bind UI components in your layout directly to data sources in your app’s code. This means you can avoid writing a lot of boilerplate code to update your UI components with data from your app.

To enable the usage of view models in your Android application, add the following
snippet to the app/build.gradle file.

```kt

plugins {
    id("org.jetbrains.kotlin.kapt")
    id ("kotlin-parcelize")
}

android {
....
    buildFeatures {
        viewBinding true
        dataBinding true
    }
}

dependencies {
    ...
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    ...
}

```

Once you’ve enabled **Data Binding**, you can start using it in your layout files by
enclosing your layout in a <layout> tag. Within the <layout> tag, you can use the @syntax
to bind data to UI components. For example in Region item Layout, you can bind a
string to a regionNameTextView and regionIdTextView like this:

```xml 

```xml
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".ui.PokemonDetailActivity"
    android:background="@color/white">

    <data>
        <variable
            name="region"
            type="<Package Path>.PokemonRegion" />

    </data>


<androidx.appcompat.widget.LinearLayoutCompat
    android:id="@+id/relativeLayoutBackground"
    android:layout_width="match_parent"
    android:layout_height="170dp"
    android:clickable="true"
    android:clipToPadding="true"
    android:focusable="true">

....

<androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/regionNameTextView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="left|center_vertical"
            android:layout_marginStart="40dp"
            android:layout_marginBottom="10dp"
            android:gravity="center|left"
            android:textColor="@color/white"
            android:textSize="20sp"
            android:textStyle="bold"
            android:text="@{region.name}"
            tools:text="Hoenn"/>

        <androidx.appcompat.widget.AppCompatTextView
            android:id="@+id/regionIdTextView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="left|center_vertical"
            android:layout_marginLeft="40dp"
            android:layout_marginTop="20dp"
            android:gravity="center"
            android:textColor="@color/white"
            android:textSize="14sp"
            android:textStyle="bold"
            android:text="@{@string/pk_generations(region.id)}"
            tools:text="1º Generation"/>
....
</layout>

```

#### Changes in item_region.xml

For the regionBgImage and regionStartersImageView we need to create a custom binding for setting the image resource (src) of an ImageView, because we need translate integer to Bitmap. Create a new Kotlin File with the name ViewBinding in ui package

```kotlin

object ViewBinding {
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageResource(imageView: AppCompatImageView, resource: Int) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context,resource))
    }
}

```

1. @JvmStatic: This annotation is used to indicate that the function should be treated
as a static method in Java. This allows the function to be called from Java code as
a static method, rather than as an instance method.
2. @BindingAdapter(”android:src”): This is a custom binding adapter annotation
provided by the Data Binding library. It specifies that this binding adapter is
intended to be used to bind the ”android:src” attribute of an ImageView in a layout
XML file.
3. fun setImageResource(imageView: AppCompatImageView, resource: Int): This is
the definition of the binding adapter function. It takes two parameters:
   1.  imageView: An instance of AppCompatImageView, which is the ImageView
to which the binding adapter is being applied.
   2.  resource: An integer value, which represents the image resource ID that needs
to be set as the source of the ImageView.
   3. imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, resource)): This line of code sets the image resource for the ImageView. It uses the
ContextCompat.getDrawable() method to retrieve a Drawable object from the given
resource ID using the context of the ImageView, and then sets it as the drawable
source of the ImageView using the setImageDrawable() method.



## Network Layer

Create a package named network within the model package. Then, create another package
named responses inside the previously created package.
We will be using the Retrofit library, which is widely used in Android development for
networking purposes. It is specifically designed to simplify network requests and handle
API integrations. Retrofit is built on top of OkHttp, another popular HTTP client library,
and offers a higher-level, declarative interface for making network calls.

1. **Setup**: To utilize Retrofit in your Android project, you need to include the necessary
dependencies in your project’s build.gradle file.