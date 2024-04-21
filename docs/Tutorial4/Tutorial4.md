# Tutorial 4

?> Review 1.1 - Add Pokemon List <br>
Review 1.2 - Correct name in menu pokemon_regions, 


> Goals
> Build a full aplication using Clean Architecture with MVVM

# App - Pokedex

## First Challenge - Data Binding

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
    @BindingAdapter("android:src", "isToSetBackground")
    fun setRegionImage(imageView: AppCompatImageView, regionName: String, isToSetBackground: Boolean) {
        if (isToSetBackground) {
            val regionImageUri = "@drawable/bg_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        } else {
            val regionImageUri = "@drawable/pk_${regionName.lowercase()}"
            val regionDrawableId = imageView.context.resources.getIdentifier(regionImageUri,null, imageView.context.packageName)
            imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, regionDrawableId))
        }
    }
}

```

**Annotations:**
1. @JvmStatic: This annotation is used to generate a static method when the Kotlin code is compiled to JVM bytecode. It's typically used when you want to call a Kotlin method from Java code.
**Binding Adapter Function:**
This function is a binding adapter, which is a way to bind custom behavior to attributes in XML layouts when using data binding in Android.
It is annotated with @BindingAdapter, indicating that it's a binding adapter function.
The function takes three parameters:
1. imageView: An AppCompatImageView object, which is the view that the binding adapter is applied to.
2. regionName: A String representing the name of the region.
3. isToSetBackground: A Boolean indicating whether to set the background or not.
<br>
**Logic:**<br>
Inside the function, there's a conditional check based on the value of isToSetBackground.
If isToSetBackground is true, it sets the background image for the imageView using the background image URI formed from the regionName. The URI is constructed dynamically based on the region name and the image is loaded using ContextCompat.getDrawable().
If isToSetBackground is false, it sets a different image (regionStartersImageView) for the imageView, following a similar approach as above but with a different URI formation.
<br>**Resource Lookup:**<br>
imageView.context.resources.getIdentifier(): This method is used to retrieve the resource identifier for the given resource name. It's used here to dynamically obtain the resource ID of the image drawable based on its name and package name.
<br>
**Usage:**<br>
This binding adapter can be used in XML layouts with data binding to dynamically set the image of an AppCompatImageView based on the provided regionName and isToSetBackground attributes.


```xml item_region.xml
...
        <androidx.appcompat.widget.AppCompatImageView
            android:id="@+id/regionBgImage"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="fitXY"
            android:foreground="#96222f3e"
            android:foregroundTintMode="src_in"
            android:src="@{region.name}"
            app:isToSetBackground="@{true}"
            tools:src="@drawable/bg_hoenn"/>


        <androidx.appcompat.widget.AppCompatImageView
            android:id="@+id/regionStartersImageView"
            android:layout_width="197dp"
            android:layout_height="120dp"
            android:layout_gravity="center|right"
            android:layout_margin="0dp"
            android:contentDescription="@string/app_name"
            android:scaleType="center"
            android:src="@{region.name}"
            app:isToSetBackground="@{false}"
            tools:src="@drawable/pk_hoenn"/>

...
```

We need to change the Regions Adapter. On ViewHolder, create a function bindView to bind the property region. And Update the function onBindViewHolder to invoke the bindView function of the current holder.

```kotlin

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val regionItemBinding = ItemRegionBinding.bind(itemView)
        fun bindView(region: PokemonRegion, itemClickedListener: OnItemClickedListener?) {
            regionItemBinding.region = region
            itemView.setOnClickListener{
                itemClickedListener?.invoke(region)
            }
        }
    }

....

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = pkRegionList[position]
        holder.bindView(region, itemClickedListener)
    }

```



Test the Region Activity.

### ViewModel

ViewModels offer a number of benefits:
1. ViewModel‘s are lifecycle-aware, which means they know when the attached Activity/
Fragment is destroyed and can immediately release data observers and other
resources.
2. They survive configuration changes, so if your data is observed or fetched through
a ViewModel, it’s still available after your Activity or Fragment is re-created. This
means you can re-use the data without fetching it again.
3. ViewModel takes the responsibility of holding and managing data. It acts as a bridge
between your Repository and the View. Freeing up your Activity or Fragment from
managing data allows you to write more concise and unit-testable code.

To enable the usage of view models in your Android application, add the following
snippet to the app/build.gradle file.

```kt
android {
....
    buildFeatures {
    ...
        viewBinding true
    ...
    }
}

```

#### Create Region View Model

```kt
class RegionsViewModel : ViewModel() {
    private val _regions = MutableLiveData<List<PokemonRegion>?>()
    val regions: LiveData<List<PokemonRegion>?>
        get() = _regions

    fun fetchRegions() {
        _regions.value = MockData.regions
    }
}

```

**RegionsViewModel Class:**
This class is a ViewModel class that extends ViewModel.
It exposes a public immutable live data regions of type List<PokemonRegion>?, allowing external classes to observe changes to the regions data.
The fetchRegions() function is a public method used to fetch regions data and update the _regions live data with mock data from MockData.regions.
LiveData and MutableLiveData:
LiveData is a lifecycle-aware observable data holder class provided by the Android Architecture Components.
MutableLiveData is a subclass of LiveData that allows modification of the stored value, making it mutable.
_regions is declared as MutableLiveData to enable updating its value within the ViewModel.
fetchRegions() <br>
**Function:**<br>
When called, this function sets the value of _regions to the mock data obtained from MockData.regions.
This method could be replaced with actual data fetching logic, such as making a network request or accessing a local database.
<br>**Usage:**<br>
This ViewModel can be used in conjunction with an activity(RegionActivity) to provide regions data to the UI.
Other components can observe changes to the regions data using the regions live data.
The fetchRegions() method can be called from the UI or other parts of the app to trigger fetching of regions data.

Let's change RegionActivity to utilize a RegionsViewModel to observe changes in regions data and update the UI accordingly.

```kt RegionActivity.kt
... 

val viewModel: RegionsViewModel by viewModels()

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val regionBinding = binding as ActivityRegionsBinding
        var listView = regionBinding.regionsRecyclerView

        viewModel.regions.observe(this) {
            listView.adapter = it?.let { it1 ->
                RegionAdapter(
                    pkRegionList = it1,
                    ...,
                    context = this
                )
            }
        }

        viewModel.fetchRegions()
 }

...
```

* Using viewModels() Delegate:
viewModels() is a property delegate provided by the fragment-ktx or activity-ktx artifact in Android Jetpack.
It's used to retrieve a ViewModel scoped to the lifecycle of the fragment or activity.
In this case, val viewModel: RegionsViewModel by viewModels() initializes viewModel with an instance of RegionsViewModel.
* onCreate() Method:
This method is overridden from the AppCompatActivity class and is called when the activity is first created.
* Binding:
val regionBinding = binding as ActivityRegionsBinding retrieves the binding object for the activity.
regionBinding.regionsRecyclerView obtains a reference to the RecyclerView where regions data will be displayed.
* Observing LiveData:
viewModel.regions.observe(this) { ... } observes changes in the regions LiveData within the ViewModel.
When the data changes, the lambda expression inside observe is executed.
* Fetching Regions Data:
viewModel.fetchRegions() is called to fetch regions data. This triggers the fetchRegions() method in the ViewModel, which updates the regions LiveData with mock data.

### Second Challenge - Start using View Model and Data Binding in PokemonListActivity and PokemonDetailActivity
In this challenge, create the viewModel for PokemonList and Pokemon Detail, and use data bindings to populate the data in the XML, as we did in the Pokemon Regions.
I'll leave the data binding functions to load the pokemon image and the pokemon function color.

```kotlin

     @JvmStatic
    @BindingAdapter("app:backgroundTint")
    fun bindBackgroundColor(view: MaterialButton, resource: Int)
    {
        view.background.setTint(ContextCompat.getColor(view.context,resource))
    }


     @JvmStatic
    @BindingAdapter("paletteImage", "paletteView")
    fun bindLoadImagePaletteView(view: AppCompatImageView, url: String, paletteView: View) {
        val context = view.context
        Glide.with(view.context)
            .asBitmap()
            .load(url)
            .listener(object : RequestListener<Bitmap>
            {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {

                    Log.d("TAG", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    model: Any,
                    p2: Target<Bitmap>?,
                    dataSource: DataSource,
                    p4: Boolean
                ): Boolean {
                    Log.d("TAG", "OnResourceReady")

                    if (resource != null) {
                        val p: Palette = Palette.from(resource).generate()

                        val rgb = p?.lightMutedSwatch?.rgb

                        val light = p?.lightVibrantSwatch?.rgb
                        val domain = p?.dominantSwatch?.rgb

                        if (domain != null) {
                            if (light != null) {

                                val gfgGradient = GradientDrawable(
                                    GradientDrawable.Orientation.TOP_BOTTOM,
                                    intArrayOf(
                                        domain,
                                        light
                                    ))

                                paletteView.background = gfgGradient
                            } else {
                                if (rgb != null) {
                                    paletteView.setBackgroundColor(rgb)

                                }
                            }

                        }
                    }
                    return false
                }
            })
            .into(view)
    }
```

## Network Layer

Create a package named network within the model package. Then, create another package
named responses inside the previously created package.
We will be using the Retrofit library, which is widely used in Android development for
networking purposes. It is specifically designed to simplify network requests and handle
API integrations. Retrofit is built on top of OkHttp, another popular HTTP client library,
and offers a higher-level, declarative interface for making network calls.

1. **Setup**: To utilize Retrofit in your Android project, you need to include the necessary
dependencies in your project’s build.gradle file.

```xml 

```