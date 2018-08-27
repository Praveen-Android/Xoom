# Xoom
Application that shows the list of countries that Xoom can send money to

# Use cases this app covers
1. Show a list of countries that xoom has active disbursements option and give the ability to favorite/remove favorite
2. On every launch, check whether database has any data saved already in which case, fetch the the list from the database
2. Prepare the final list with favorites (from Shared preferences) at the top followed by rest of the countries
3. On data refresh, (tap on refresh icon), delete the existing db, fetch the list from network call and insert them to the db (Query could be optmized based on how frequently the data changes)
4. Favorites are preserved and shown at the top of the list across app restarts (kill & relaunch (or) otate the screen)
5. Simple UI with a bit of animation when displaying the list

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

# Overall Thoughts
1. The application is developed assuming that there is no rest api available to post/delete a favorite. So maintaining the list of favorites is done locally and is preserved through db and network calls.
2. If the post/delete service was available then the approach would be different as in when the user favorites a country, a post to favorite call will be made and vice versa for removing a favorite. On refresh (UI refresh click), the local favorites data would be synced with remote data
3. The favorites are stored in shared preferences but they could be saved to a seperate table or as an extra column in the existing DB. This would have been a simpler approach if we had the post/delete api call so that we could always sync db with network. I believe that if the number of favorites were to get really high, storing it to a db would be more preferable.
4. I've added some unit tests to the code but obviously more UT's needs to be added and is very much doable with the flexible architecture we have here.
5. I haven't added any code to sort rest of the list (minus favorites) probably because I did not find any specific requirement to do so but can definitely be done here.
