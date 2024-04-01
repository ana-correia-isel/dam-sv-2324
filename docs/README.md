# Theoretical

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


## Clean Architecture

Clean Architecture is a software architectural pattern for developing Android applications
that focuses on separation of concerns, testability, and maintainability. It provides a
structured approach to organizing code into different layers, each with a specific responsibility. These layers interact with each other in a specific way to create a modular and
scalable architecture.
The main layers in Clean Architecture for Android are:
1. **Presentation Layer**: This layer is responsible for rendering the user interface and
handling user interactions. It includes components such as activities, fragments,
views, or view models (depending on the chosen architecture pattern, such as MVP
or MVVM ). The presentation layer is responsible for displaying data to the user and
capturing user inputs, but it should not contain business logic or data manipulation.
It communicates with the domain layer to request data and trigger actions based
on user interactions.
2. **Domain Layer**: Also known as the business logic layer, this layer encapsulates the
core logic of the application. It includes use cases (or interactors) that represent
the individual actions or operations that can be performed by the application. Use
cases are independent of any framework or external dependencies and define the
business rules and logic of the application. The domain layer communicates with
the data layer to fetch or store data, and it defines the contracts or interfaces that
are implemented by the data layer.
3. **Data Layer**: This layer is responsible for fetching and storing data from external
sources, such as databases, APIs, or repositories. It includes components such
as data sources, repositories, and mappers that handle data conversion between
different layers. The data layer implements the contracts or interfaces defined in
the domain layer and provides the data in a format suitable for the domain layer.
The data layer may also handle caching, offline data access, or other data-related
operations.

The iterations in Clean Architecture refer to the flow of data and control between the
layers. The typical flow of data and control in Clean Architecture is as follows:
1. The **Presentation Layer** communicates with the Domain Layer to request data or
trigger actions based on user interactions.
2. The **Domain Layer** contains the business logic and rules of the application and
communicates with the Data Layer to fetch or store data.
3. The **Data Layer** is responsible for fetching and storing data from external sources
and implementing the contracts or interfaces defined in the Domain Layer.
4. The **Data Layer** provides the requested data back to the Domain Layer, which then
processes it and returns the result to the Presentation Layer.
5. The **Presentation Layer** renders the user interface or takes appropriate actions based
on the data received from the Domain Layer.


It’s important to note that Clean Architecture promotes a one-way flow of data and
control, with dependencies pointing inward, from the outer layers to the inner layers.
This ensures that the inner layers, such as the Domain Layer, are independent of any
framework or external dependencies, making them highly testable and maintainable.


## JetPack

Most Android apps now use the support libraries to help users add all kinds of updated
widgets and to address compatibility issues across Android devices and OS versions. You’d
be hard-pressed to find an Android app that doesn’t use something from them, and they’re
included as a dependency in template projects made in Android Studio. Widgets as
fundamental as RecyclerView are included in them.
The support libraries are great to use, but they have evolved quite a bit over the years
and the names have become somewhat confusing. There are `com.android.support:supportv4 and com.android.support:support-v13`, for example. 
Looking at the names alone, do
you know what classes are in them? The names are supposed to designate what version
of Android they support, but, as they have evolved, the minimum version has increased
to API Level 14.
Google has realized that this variety of ever-changing libraries is very confusing for new
(and old) developers and has decided to start over. Consequently, it has created Android
Jetpack that is of components, tools and guidelines, that aims to facilitate the creation
of applications. As shown in Figure 4, the Android Jetpack components bring together
the support library which is a group of components that facilitate the use of the new
Android features and the Architecture Components that was designed to facilitate data
management during system changes application lifecycle. The components are organized
into four categories:

1. Architecture;
2. UI;
3. Behavior;
4. Foundation.

![](/assets/Jetpack.png)

### Architecture Components

It is a collection of libraries that help to develop applications robust, testable and easy to
maintain. This library helps to manage the life cycle of UI components and better control
the persistence of data even with changes in the application life cycle. In the next topics
we present the most importants libraries that make part of Architecture Components.

#### Room

If you have ever struggled working with the SQLite database in an Android app, you
will appreciate what the Room library does for you. You create several simple classes
that define your data and how to access them, and the Room library will do most of
the rest. The only SQL code you have to write is for queries, which are usually pretty
straightforward. And you gain compile-time checks of your SQL code when using Room.
There are three important classes you need to use with Room: Database (this contains
your main entry point and holds a reference to the database object for the app), Entity
(you create one for each table in the database), and DAO (this contains the methods for
retrieving and managing the data)

#### Lifecycle

The Lifecycle library helps you listen for lifecycle events in other components besides an
activities and fragments. This allows you to have lifecycle-aware logic in places other than
just an Activity or Fragment. The library works by using annotations on methods so you
get notified for the events that you are interested in.
You implement LifecycleObserver, annotate methods and add this observer to a lifecycle owner, which is usually an Activity or Fragment. The LifecycleObserver class
can get the current lifecycle state by calling lifecycle.getCurrentState() and can use this
information to avoid calling code when not in the correct state.
A LifecycleOwner is an object that has Android lifecycle events. The support library
Activity and Fragment classes already implement the LifecycleOwner methods. A LifecycleOwner has a Lifecycle object that it can return to let the observer know what the
current state is.

#### ViewModel

While the Room library persists your data in permanent storage, the ViewModel class
allows you to hold onto data in device memory in a lifecycle-aware manner. One of the
nice features of a ViewModel is that it can survive the re-construction of an Activity or
Fragment over a configuration change such as a device rotation. The system will hold
onto that ViewModel re-associate it with the Activity or Fragment. ViewModels are also
where you can load data in the background and use LiveData to notify listeners that the
data is ready.

#### LiveData

The LiveData library uses the Observer pattern for data but handles it in a lifecycle-aware
manner. You get the benefits of automatic UI updates when data changes without calling
UI elements when the UI is not in the correct state.
LiveData is the class that implements the observer pattern, holds that data, and
notifies listeners when that data has changed.

#### Paging

Have you ever had to deal with large amounts of related data? Maybe too much for you to
download at once? The Paging library will help by providing ways to handle the paging
of data in a RecyclerView.
The Paging library uses several key classes: PagedList, PagedListAdapter, and DataSource. PagedList is a list that loads data lazily from a DataSource, allowing the app to
load data in chunks or pages. PagedListAdapter is a custom RecyclerView.Adapter that
handles pages with a DiffUtil callback.
For the DataSource, you will use one of three different subclasses: PageKeyedDataSource, ItemKeyedDataSource, or PositionalDataSource.


#### WorkManager

Over the years, there have been several systems built into Android for handling background jobs or alarms. They differ on different versions of Android and you have to write
a lot of code to handle the different versions of the OS.
WorkManager solves this problem and gives you one library for creating deferrable,
asynchronous tasks and defining when they should run. You can define one-time jobs or
repeating jobs.


## MVVM in Android

MVVM (Model-View-ViewModel) is a widely used architectural pattern in Android app
development that provides a clear separation of concerns and promotes a modular, maintainable, and testable codebase. When used in conjunction with Clean Architecture,
MVVM can help create scalable and maintainable Android applications. Here’s how
MVVM can be implemented in Clean Architecture in an Android app using Jetpack
components:
1. Model: The Model in MVVM represents the data and business logic of the application. In Clean Architecture, it typically includes the following components:
2. Entity: It represents the domain model, which is a pure Kotlin/Java representation
of the data entities in the application. It contains only data and no business logic.
3. Repository: It acts as an interface between the data sources (e.g., local database,
remote server) and the domain layer. It defines the contract for accessing and managing data, and it can implement different data sources based on the application’s
requirements.
4. Use Case/Interactor: It encapsulates the business logic of the application, defining
the different use cases or interactions that can be performed by the application. It
communicates with the repositories to fetch or modify data and defines the input
and output models for each use case.
5. View: The View represents the UI components of the application, including activities, fragments, and views. In MVVM, the View is passive and only responsible for
displaying data and capturing user input. It does not contain any business logic.
The View observes the ViewModel and updates the UI based on the data received
from the ViewModel.
6. ViewModel: The ViewModel acts as an intermediary between the View and the
Model. It holds the UI-related state and business logic of the View. It exposes
data to the View through observable properties, and it receives input from the View
through commands or events. The ViewModel communicates with the Model to
fetch or modify data and maps the data into a format that can be easily consumed
by the View. The ViewModel is also responsible for handling UI-related events,
such as user input validation and navigation.
7. Data Binding: MVVM in Android often utilizes data binding, which is a feature
provided by Android Jetpack. Data binding allows for declarative binding of UI components to data models, eliminating the need for manual UI updates. This means
that the View automatically updates when the data in the ViewModel changes, and
vice versa.
8. Observables: Observables are used to enable communication between the View and
the ViewModel. The ViewModel exposes observables (e.g., LiveData, ObservableField) that the View observes to get notified of data changes. When data changes in
the Model or ViewModel, the observables emit notifications, which trigger updates
in the View.
9. Jetpack Components: Android Jetpack provides several components that can be
used in MVVM, such as LiveData and ViewModel. LiveData is a lifecycle-aware
observable data holder that can be used to expose data from the ViewModel to the
View, and it automatically handles UI updates based on the lifecycle of the View.
ViewModel is a lifecycle-aware component that is designed to store and manage
UI-related data, and it survives configuration changes, such as screen rotations.