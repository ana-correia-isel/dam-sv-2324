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


# Let’s Start

In this tutorial, the focus will be mainly on the Presentation layer, although for testability,
the base model of the application will be implemented, and fictitious (mock) data and
some rules will be used in the Domain layer. 
In Tutorial 4, the focus will be on the Model and Domain layers.

First, create a new project using "Empty Views Activity" and create 3 folder in principal packge: **model**, **domain** and **ui**.

In the Moodle, you will find a zip file with the resources that will be used in the
project. These resources are divided into the following folders:
1. drawable
2. strings
3. colors
