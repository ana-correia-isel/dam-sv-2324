# Tutorial 3 - PART 1

> Goals

## Introduction

In the next two tutorials, we will develop a more complex application following patterns
used in professional Android app development, and we will also cover Jetpack, which is
a collection of libraries developed by Google that assist in application development and contain some of the patterns we will address.

Follows a set of links that you should consult during the development of this work:
 * Android Jetpack: https://developer.android.com/jetpack
 * Clean Architecture - https://developer.android.com/topic/architecture
 * Android JetPack - LiveData: https://developer.android.com/topic/libraries/architecture/livedata
 * Android JetPack - Room: https://developer.android.com/training/data-storage/room
 * Android JetPack - ViewModel: https://developer.android.com/topic/libraries/architecture/viewmodel
 * Android JetPack - Data Binding : https://developer.android.com/topic/libraries/data-binding/
 * Android JetPack - Lifecycle: https://developer.android.com/topic/libraries/architecture/lifecycle
 * Android JetPack - Navigation: https://developer.android.com/guide/navigation
 * Android JetPack - Paging: https://developer.android.com/topic/libraries/architecture/paging/v3-overview
 * Android JetPack - WorkManager: https://developer.android.com/topic/libraries/architecture/workmanager


## APP - POKEDEX

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
| ![](/assets/Pokemon-Regions-List.png) | ![](/assets/Pokemon-Regions-List.png) | ![](/assets/Pokemon-Regions-List.png) |



# Let’s Start

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

## Create Bottom navigation

/// DOC https://github.com/material-components/material-components-android/blob/master/docs/components/BottomNavigation.md

### Create Bottom Menu

// DOCUMENTATION

Create a menu folder using the steps that show the gif and create the xml file bottomnavmenu.xml.

![Create menu folder](https://media.giphy.com/media/7RoYmHrHihmJn3buvF/giphy.gif)


#### Create a bottomnavmenu.xml 

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/navigation_pokedex"
        android:icon="@drawable/ic_regions"
        android:title="@string/app_bottomtabbar_pokedex_title" />

    <item
        android:id="@+id/navigation_teams"
        android:icon="@drawable/ic_team"
        android:title="@string/app_bottomtabbar_team_title" />
</menu>
```

#### Create a bottomnavigation.xml 

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
    app:menu="@menu/bottomnavigation" />
```

#### Create a BottomNavActivity

```kt
abstract class BottomNavActivity : AppCompatActivity() {

    lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        navigationView = findViewById(R.id.navigation)
        navigationView.itemIconTintList = null
        navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_pokedex -> {
                    startActivity(Intent(this, PokedexActivity::class.java))
                    true
                }
                R.id.navigation_teams -> {
                    startActivity(Intent(this, TeamActivity::class.java))
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
Note: will be showing the Regions Activity code in this doc, and replicate to Teams Activity the same logic.

##### RegionsActivity Code

```kt
abstract class BottomNavActivity : AppCompatActivity() {

    lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentViewId)
        navigationView = findViewById(R.id.navigation)
        navigationView.itemIconTintList = null
        navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_pokedex -> {
                    startActivity(Intent(this, PokedexActivity::class.java))
                    true
                }
                R.id.navigation_teams -> {
                    startActivity(Intent(this, TeamActivity::class.java))
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

##### RegionsActivity XML

### First Challenge - Implement the Regions Activity using the following design.

Note: Not forget that this screen show a list and each cell is a regions (Reclyt View)

