# Sample - Android Architecture Sample

A Sample MVVM, simple app that loads information from [The reqres API](https://reqres.in/api/) .
Including:

* ViewModel
* LiveData
* Hilt (for dependency injection)
* Kotlin Coroutines
* Retrofit
* Room
* Navigation
* Bind Service

## Project Structure

*background: where our services run in background thread
*data: Our M (Model) in MVVM. Where we perform data operations.
*di: Dependency Injection with the help of Hilt.
*ui: Our Fragments and ViewModels helping to display data to the user.
*utils: Helper classes and functions.