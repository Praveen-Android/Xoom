# Xoom
Application that shows the list of countries that Xoom can send money to

# App Architecture
https://github.com/Praveen-Android/Xoom/blob/master/App_Architecture.png

#### The app has following packages:
1. **data**: It contains all the data access class
2. **di**: Dependency providing classes using Dagger2.
3. **ui**: View classes (activites/fragments) along with View Models.
4. **api**: Api services used in the application.
5. **utils**: Utility classes used in the application.
6. **repo**: provides all the essentail methods to interact with application's backend
7. **prefs**: provides shared preferences for the application
8. **database**: provides database for the application

#### Technology stack used
1. **Kotlin** - Concise, less boiler plate code, null safe type system, smart cast and smart conditional statements
2. **RxJava2** - Powerful library with useful operators for asynchronous processing of of data as streams
3. **Dagger2** - for dependency injection so that each class can function independently and delete the creation of dependencies to Dagger 2 and due to this separation of concerns, everything can be tested in isolation 
4. **Glide** - Fluid library used for image and bitmap processing and has vast capabilities to perform various transformations
5. **RecyclerView** - Show list of items in grid layout view. Has vast improvements over a list view in terms of performance and code   structure
6. **Retrofit** - type safe rest client that provides ease of implementation, abstraction in terms of response parsing and works well with RxJava2 and Android Architectural components
7. **Android Architecture Components** - View Model and Live Data for MVVM architecture and more sepration of concerns thereby providing the flexibility
8. **Espresso** - provides framework for funtional UI testing

# Assumptions/Premise
1. No post favorite api available and so did the maintenance and syncing of user favorites locally

# Enhancements
1. Add more Unit & Espresso tests which is achievable since we have have a flexible architecture
