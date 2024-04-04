# Tutorial 3 - PART 1

?> Review 1.1 - Add Pokemon List <br>
Review 1.2 - Correct name in menu pokemon_regions


> Goals
>  Build a full aplication using Clean Architecture with MVVM
> 
# APP - POKEDEX

The application we will implement is the Pokedex, which will have the following functionalities:
1. List the regions of the Pokemon franchise.
2. List the existing Pokemon in a selected region.
3. List all existing Pokemon in the Pokemon franchise.
4. Display details of the selected Pokemon from the Pokemon list.
5. List the Pokemon team created by the user.
6. Create user teams.
7. Add/Remove Pokemon from the team.

#### Screens

| Pokemon Regions List | Pokemon List | Pokemon Detail |
|:--------------------:|:------------:|:--------------:|
| ![](/assets/Pokemon-Regions-List.png) | ![](/assets/Pokemon_list.png) | ![](/assets/PokemonDetail.png) |



## Let’s Start

In this tutorial, the focus will be mainly on the Presentation layer, although for testability,
the base model of the application will be implemented, and fictitious (mock) data and
some rules will be used in the Domain layer. 
In Tutorial 4, the focus will be on the Model and Domain layers.

In the Moodle, you will find a zip file with the resources that will be used in the
project. These resources are divided into the following folders:
1. drawable
2. strings
3. colors

First, create a new project using "Empty Views Activity" and copy or import these files into the new project.
Create the following packages(Figure 6):
1. ui - Contains all the classes of UI (Activities and ViewModels).
2. domain - Contains the classes that handle uses cases operations.
3. data - Contains the application’s data model classes.

### Inicial Model
We will create the necessary basic entities of our data model to be able to test the
application. (create each class in model package)

1. PokemonRegion: which will contain the base information of the region - id, name,
backgroundImage, startersImage

```kt

data class PokemonRegion(
    var id: Int,
    var name: String,
    @DrawableRes val bg: Int,
    @DrawableRes val starters: Int
)

```

2. PokemonType: which will contain the information of the pokemon type - id,
name, iconImage, color 

```kt

data class PokemonType(var id: Int,
                       var name:String,
                       @DrawableRes val icon: Int,
                       @ColorRes val color: Int)

```

3. Pokemon: which will contain the base information of the pokemon - id, name,
imageUrl - Implement ourself
4. PokemonDetail: which will contain the detailed information of the pokemon - id,
pokemon, description, weight, height, types  - Implement ourself

As one of the requirements of the application is to consume data using the POKEAPI,
but we will only implement it in the next tutorial, it is necessary to use mock data to test
the application. For this purpose, we will create the static object MockData,
which is located in the package model/mocks

```kt
object MockData {

    private val POKEMONS_SIZE = 100

    private var pokemonDetailDescription: String = "Pokem ipsum dolor " +
            "sit amet Crustle Grotle" +
            " Dragonair Palkia Shellder Terrakion. " +
            "Hive Badge Pokeball Spinda Seedot James Vullaby " +
            "Helix Fossil. Water Gun Professor Oak Marowak Spearow " +
            "Dunsparce Chimchar Nidorino." +
            " Silver Azumarill Tyranitar Trubbish " +
            "Fighting sunt in culpa qui officia Mothim. " +
            "Celadon City Mantine Clefable Piplup Scizor " +
            "excepteur sint occaecat cupidatat non proident Terrakion."


    var regions = listOf<PokemonRegion>(
        PokemonRegion(1, "Kanto", R.drawable.bg_kanto, R.drawable.pk_kanto),
        PokemonRegion(2, "Johto", R.drawable.bg_johto, R.drawable.pk_johto),
        PokemonRegion(3, "Hoenn", R.drawable.bg_hoenn, R.drawable.pk_hoenn),
        PokemonRegion(4, "Sinnoh", R.drawable.bg_sinnoh, R.drawable.pk_sinnoh),
        PokemonRegion(5, "Unova", R.drawable.bg_unova, R.drawable.pk_unova),
        PokemonRegion(6, "Kalos", R.drawable.bg_kalos, R.drawable.pk_kalos),
        PokemonRegion(7, "Alola", R.drawable.bg_alola, R.drawable.pk_alola),
        PokemonRegion(8, "Galar", R.drawable.bg_galar, R.drawable.pk_galar),
    )

    var pokemonTypeMock= listOf<PokemonType>(
        PokemonType(1,"water", R.drawable.water, R.color.water),
        PokemonType(2,"fire", R.drawable.fire, R.color.fire),
        PokemonType(3,"bug", R.drawable.bug, R.color.bug),
        PokemonType(4,"ghost", R.drawable.ghost, R.color.ghost),
        PokemonType(5,"grass", R.drawable.grass, R.color.grass),
        PokemonType(6,"ground", R.drawable.ground, R.color.ground),
        PokemonType(7,"rock", R.drawable.rock, R.color.rock),
        PokemonType(8,"dark", R.drawable.dark, R.color.dark),
        PokemonType(9,"dragon", R.drawable.dragon, R.color.dragon),
        PokemonType(10,"electric", R.drawable.electric, R.color.electric),
        PokemonType(11,"fairy", R.drawable.fairy, R.color.fairy),
        PokemonType(12,"fighting", R.drawable.fighting, R.color.fighting),
        PokemonType(13,"ice", R.drawable.ice, R.color.ice),
        PokemonType(14,"normal", R.drawable.normal, R.color.normal),
        PokemonType(15,"psychic", R.drawable.psychic, R.color.psychic),
        PokemonType(16,"flying", R.drawable.flying, R.color.flying),
        PokemonType(17,"poison", R.drawable.poison, R.color.poison),
        PokemonType(18,"steel", R.drawable.steel, R.color.steel)
    )

      /*var pokemons = (1..POKEMONS_SIZE).map {
          Pokemon(it,
              "bulbasaur",
              "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                      "/sprites/pokemon/other/official-artwork/${it}.png",
              regions.random(), pokemonTypeMock.asSequence().shuffled().take(2).toList()
          )
      }*/

   /* var pokemons = listOf(
        Pokemon(1,
            "bulbasaur",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/1.png"

        ),
        Pokemon(4,
            "charmander",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/1.png"
        ),
        Pokemon(6,
            "squirtle",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/7.png",
        ),
        Pokemon(10,
            "caterpie",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/10.png",),
        Pokemon(13,
            "weedle",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/13.png"),
        Pokemon(16,
            "pidgey",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/16.png"),
        Pokemon(19,
            "rattata",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/19.png"),
        Pokemon(21,
            "spearow",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/21.png"),
        Pokemon(23,
            "ekans",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/23.png"),
        Pokemon(25,
            "pikachu",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/25.png"),
        Pokemon(27,
            "sandshrew",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/27.png"),
        Pokemon(29,
            "nidoran",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/29.png"),
        Pokemon(35,
            "clefairy",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/35.png"),
        Pokemon(37,
            "vulpix",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/37.png"),
        Pokemon(39,
            "jigglypuff",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/39.png"),
        Pokemon(41,
            "zubat",
            "https://raw.githubusercontent.com/PokeAPI/sprites/master" +
                    "/sprites/pokemon/other/official-artwork/41.png"),


        )*/

    /*  var pokemonDetail = pokemons.map {
          PokemonDetail(
              it,
              pokemonDetailDescription,
              pokemonTypeMock.asSequence().shuffled().take(1).toList(),
              ( Random.nextDouble(20.0,50.0) * 100.0).roundToInt() / 100.0,
              (Random.nextDouble(0.20, 2.0) * 100.0).roundToInt() / 100.0,
              PokemonStats(),
              generateSequence {
                  PokemonEvolution(1, pokemons.random(), false,
                      0,"", 0, "")
              }.take(Random.nextInt(1,3)).toList()
          )
      }*/
}
```

### First Challenge - Implement Bottom Navigation 

**Documentation**:
- https://m2.material.io/components/bottom-navigation/android
- https://github.com/material-components/material-components-android/blob/master/docs/components/BottomNavigation.md

In Android, bottom navigation refers to a user interface pattern where the navigation options are arranged at the bottom of the screen. This pattern is often used in apps to allow users to easily switch between different sections or functions.
When implementing bottom navigation with activities in Android, each navigation option usually corresponds to a separate activity. An activity represents a single screen with a user interface that the user can interact with.
Guide on how to implement bottom navigation with activities in Android:

* **Create Activities**: First, you need to create separate activities for each area or feature of your app that you want to navigate to with Bottom Navigation. For example, if your app has three main areas, create three activities, one for each area.

* **Design layouts**: Design the layout for each activity according to its functionality. These layouts define the user interface for each screen of your app.

* **Implement bottom navigation** : In your main activity layout file (usually activity_main.xml), add a Bottom Navigation View element. This view will contain the navigation options at the bottom of the screen.

* **Define menu items**: Define menu items for each navigation option in the Bottom Navigation View. Each menu item corresponds to one of the activities you have previously created.

* **Handle item selection**: Implement code to handle item selection events in your main activity. When a user selects a navigation option in the bottom navigation view, switch to the corresponding activity.

##### Code for Bottom Navigation

Create a menu folder using the steps that show the gif and create the xml file bottomnavmenu.xml.
![Create menu folder](https://ana-correia-isel.github.io/dam-sv-2324/Tutorial3/assets/create_menu.gif)

#### Create a BottomNavActivity - Base class to not replicate the same in multiple activities

```kt
abstract class BottomNavActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(contentViewId)
        navigationView = findViewById(R.id.navigation)
        navigationView.itemIconTintList = null
        navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_regions -> {
                    // Navigate to RegionsActivity
                    startActivity(Intent(this, RegionsActivity::class.java))
                    true
                }
                R.id.navigation_teams -> {
                    // Navigate to TeamActivity
                    startActivity(Intent(this, TeamsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    // Remove inter-activity transition to avoid screen tossing on tapping bottom navigation items
    public override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }


    private fun updateNavigationBarState() {
        val actionId = navigationMenuItemId
        selectBottomNavigationBarItem(actionId)
    }

    private fun selectBottomNavigationBarItem(itemId: Int) {
        val item = navigationView!!.menu.findItem(itemId)
        item.setChecked(true)
    }

    abstract val contentViewId: Int
    abstract val navigationMenuItemId: Int
}
```

Now create two activities, RegionActivity and TeamActivity that extends of BottomNavActivity and implement the members of that class.
**Note:** will be showing the Regions Activity code in this doc, and replicate to Teams Activity the same logic.

##### RegionsActivity Code

```kt
class RegionsActivity : BottomNavActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override val contentViewId: Int
        get() = R.layout.activity_regions
    override val navigationMenuItemId: Int
        get() = R.id.navigation_regions
}
```

##### RegionsActivity XML
```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/container"
    tools:context=".ui.RegionsActivity"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/app_bottomtabbar_regions_title"
        android:layout_centerInParent="true"
        android:textSize="36sp" />

    <include
        android:id="@+id/navigation"
        layout="@layout/bottomnavigation"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true" />

</RelativeLayout>
```

#### Create a bottomnavmenu.xml 

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/navigation_regions"
        android:icon="@drawable/ic_regions"
        android:title="@string/app_bottomtabbar_regions_title" />
    <item
        android:id="@+id/navigation_teams"
        android:icon="@drawable/ic_team"
        android:title="@string/app_bottomtabbar_team_title" />
</menu>
```

#### Create a bottomnavigation.xml - this control is to be include in each activity layouts

```xml
<?xml version="1.0" encoding="utf-8"?>
<com.google.android.material.bottomnavigation.BottomNavigationView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/navigation"
    app:labelVisibilityMode="labeled"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?android:attr/windowBackground"
    app:menu="@menu/bottomnavmenu" />
```

Finally change the Android manifest to start in Regions Activity and run the aplication.


### Second Challenge - Implement the Regions Activity using the following design.

Note: Not forget that this screen show a list and each cell is a regions (Recycler View)
** Documentation **: https://developer.android.com/develop/ui/views/layout/recyclerview

#### RecyclerView

RecyclerView is an advanced and flexible version of ListView, which is a commonly used
widget in Android to display a scrollable list of data. RecyclerView is part of the Android
Jetpack library and offers several advantages over ListView, such as better performance,
extensibility, and a more flexible layout manager system. RecyclerView recycles the views
that are no longer visible on the screen, reducing the memory usage of the app and
improving its performance. The RecyclerView consists of three main components:

1. Layout Manager: It is responsible for measuring and positioning the items in the
RecyclerView. It can be either a built-in layout manager like LinearLayoutManager,
GridLayoutManager, or StaggeredGridLayoutManager, or a custom layout manager.
2. Adapter: It is responsible for providing the views that represent the data items in
the RecyclerView. It creates a view holder for each item and binds the data to the
views in the ViewHolder.
3. ViewHolder: It holds the views that represent the data items in the RecyclerView.
It provides a reference to these views so that the adapter can bind the data to them.

The Adapter is a crucial component of the RecyclerView as it provides a bridge
between the data source and the views that represent that data in the RecyclerView.
The Adapter is responsible for creating and managing the ViewHolder objects, which are
responsible for holding references to the views that represent each item in the Recycler-
View. The Adapter also binds the data to the views in the ViewHolder.
The Adapter class is an abstract class, and you need to extend it and implement its
three methods:

1. onCreateViewHolder() : This method creates a new ViewHolder object for each
item in the RecyclerView. It inflates the layout for the item view and returns a new
instance of the ViewHolder.
2. onBindViewHolder(): This method binds the data to the views in the ViewHolder.
It takes the position of the item in the data source and updates the
views in the ViewHolder with the corresponding data.
3. getItemCount(): This method returns the total number of items in the data
source.

First let’s insert the Region Activity layout a RecyclerView with the LinearLayoutManager.

```xml
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/regionsRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layoutManager="LinearLayoutManager"
        tools:listitem="@layout/item_region"/>

```

Second, let’s create the region item layout, for that you have to add a new Layout
Resource File and add the fowolling xml code:

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.appcompat.widget.LinearLayoutCompat
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:id="@+id/relativeLayoutBackground"
        android:layout_width="match_parent"
        android:layout_height="170dp"
        android:clickable="true"
        android:clipToPadding="true"
        android:focusable="true">

        <com.google.android.material.card.MaterialCardView
            android:layout_width="match_parent"
            android:layout_height="170dp"
            android:layout_margin="0dp"
            app:cardCornerRadius="20dp"
            app:cardElevation="5dp"
            app:cardPreventCornerOverlap="true"
            app:cardUseCompatPadding="true"
            android:padding="0dp">

            <androidx.appcompat.widget.AppCompatImageView
                android:id="@+id/regionBgImage"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:scaleType="fitXY"
                android:foreground="#96222f3e"
                android:foregroundTintMode="src_in"
                tools:src="@drawable/bg_hoenn"/>


            <androidx.appcompat.widget.AppCompatImageView
                android:id="@+id/regionStartersImageView"
                android:layout_width="197dp"
                android:layout_height="120dp"
                android:layout_gravity="center|right"
                android:layout_margin="0dp"
                android:contentDescription="@string/app_name"
                android:scaleType="center"
                tools:src="@drawable/pk_hoenn"/>

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
                tools:text="1º Generation"/>

        </com.google.android.material.card.MaterialCardView>

    </androidx.appcompat.widget.LinearLayoutCompat>
```

Third, let’s create the Region Adapter with the ViewHolder.

```kt
class RegionAdapter(
    private val pkRegionList: List<PokemonRegion>,
    private val context: Context
) : RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bgImageView = itemView.findViewById<AppCompatImageView>(R.id.regionBgImage)
        val startersImageView = itemView.findViewById<AppCompatImageView>(R.id.regionStartersImageView)
        val regionTitleTextView = itemView.findViewById<AppCompatTextView>(R.id.regionNameTextView)
        val regionSubtitleTextView = itemView.findViewById<AppCompatTextView>(R.id.regionIdTextView)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_region, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val region = pkRegionList[position]
        holder.bgImageView.setImageResource(region.bg)
        holder.startersImageView.setImageResource(region.starters)
        holder.regionTitleTextView.text = region.name
        holder.regionSubtitleTextView.text = region.id.toString() + " Generation"
    }

    override fun getItemCount(): Int {
        return pkRegionList.size
    }
}
```

Let's breakdown of the code, within the RegionAdapter class, there is an inner class ViewHolder, responsible for holding references to the views inside each item of the RecyclerView. These views include an image view for background (bgImageView), an image view for starters (startersImageView), and two text views for region title (regionTitleTextView) and region subtitle (regionSubtitleTextView).
The onCreateViewHolder() method inflates the layout for each item of the RecyclerView using a LayoutInflater, returning a new instance of ViewHolder.
The onBindViewHolder() method is responsible for binding data from the pkRegionList to the views held by the ViewHolder. It sets the background image, starters image, region title, and region subtitle based on the data at the current position.
The getItemCount() method determines the total number of items in the pkRegionList, which defines the number of items in the RecyclerView.

For the last, In the onViewCreated() method, on Region Activit, the RecyclerView adapter is set up using the RegionAdapter class, which takes a list of PokemonRegion objects and a context.

```kt
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var listView = findViewById<RecyclerView>(R.id.regionsRecyclerView)
        listView.adapter = RegionAdapter(pkRegionList = MockData.regions, context = this)
    }
```

### Thrid Challenge - Implement the Pokemon List Activity using the following design.

In this challenge, the objective is for the student to implement the Pokemon List Activity following the design presented at the beginning of the tutorial, and to incorporate the functionality such that upon clicking on a region, navigation to this activity occurs.


?> **_NOTE:_** 

#### item_pokemon.xml

```xml
 <?xml version="1.0" encoding="utf-8"?>
<androidx.appcompat.widget.LinearLayoutCompat
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/relativeLayoutBackground"
    android:layout_width="match_parent"
    android:layout_height="170dp"
    android:clickable="true"
    android:clipToPadding="true"
    android:focusable="true">

    <com.google.android.material.card.MaterialCardView
        android:id="@+id/cardView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="6dp"
        android:foreground="?attr/selectableItemBackground"
        app:cardBackgroundColor="@color/white"
        app:cardCornerRadius="14dp"
        app:cardElevation="4dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:cardBackgroundColor="@color/water">

        <androidx.constraintlayout.widget.ConstraintLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:foreground="?attr/selectableItemBackground"
            app:cardBackgroundColor="@color/white">

            <androidx.appcompat.widget.AppCompatImageView
                android:id="@+id/pkImage"
                android:layout_width="90dp"
                android:layout_height="90dp"
                android:layout_margin="20dp"
                android:layout_marginTop="5dp"
                android:layout_marginEnd="10dp"
                android:layout_marginBottom="10dp"
                android:adjustViewBounds="true"
                android:scaleType="fitCenter"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/pkID" />

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/pkName"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:elevation="4dp"
                android:gravity="center"
                android:maxLines="1"
                android:padding="12dp"
                android:textColor="@color/white"
                android:textSize="20sp"
                android:textStyle="bold"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                tools:text="Charmander"/>

            <androidx.appcompat.widget.AppCompatTextView
                android:id="@+id/pkID"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginTop="10dp"
                android:layout_marginEnd="10dp"
                android:alpha="0.25"
                android:gravity="top"
                android:textColor="@color/black"
                android:textSize="18sp"
                android:textStyle="bold"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                tools:text="#4"/>
        </androidx.constraintlayout.widget.ConstraintLayout>
    </com.google.android.material.card.MaterialCardView>
</androidx.appcompat.widget.LinearLayoutCompat>
```

#### activity_pokemonlist.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/container"
    tools:context=".ui.RegionsActivity"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/pksRecyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="50dp"
        android:clipToPadding="false"
        android:fadeScrollbars="false"
        android:padding="6dp"
        android:scrollbarStyle="outsideOverlay"
        android:scrollbars="vertical"
        app:layoutManager="GridLayoutManager"
        app:spanCount="2"
        tools:listitem="@layout/item_pokemon"
        tools:spanCount="2"
        />

</RelativeLayout>
```

#### PokemonsAdapter 

```kt
class PokemonsAdapter(
    private val pokemonList: List<Pokemon>,
    private val context: Context
) : RecyclerView.Adapter<PokemonsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
        val pkImageView = itemView.findViewById<AppCompatImageView>(R.id.pkImage)
        val pkNameTextView = itemView.findViewById<AppCompatTextView>(R.id.pkName)
        val pkIDTextView = itemView.findViewById<AppCompatTextView>(R.id.pkID)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonsAdapter.ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Glide.with(holder.pkImageView.context)
            .asBitmap()
            .load(pokemon.imageUrl)
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
                        if (rgb != null) {
                            holder.cardView.setCardBackgroundColor(rgb)
                        }
                    }
                    return false
                }
            })
            .into(holder.pkImageView)
        holder.pkNameTextView.text = pokemon.name
        holder.pkIDTextView.text = "#" + pokemon.id
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
```

?> **_NOTE:_** - Add in build.gradle.kts(Module:app) in dependencies - implementation("com.github.bumptech.glide:glide:4.16.0")
<br> Add INTERNET permission on Android Manifest
<br> Add PokemonList Activity on Android Manifest

#### PokemonList Activity 

```kt
class PokemonListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemonlist)
        var listView = findViewById<RecyclerView>(R.id.pksRecyclerView)
        listView.adapter = PokemonsAdapter(pokemonList = MockData.pokemons, context = this)
    }
}
```