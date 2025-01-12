# Currency Exchanger

Currency Exchanger is an Android application designed to facilitate currency conversion with real-time updates. This app provides a user-friendly interface to check currency balances, perform conversions, and manage transaction data efficiently.

---

## Features

- Conversion between multiple currencies.
- Error handling for insufficient balance and incorrect amounts.
- 0.7% transaction fee applied after the 5th transaction.
- Persistent storage using Room database for user balance.
- Preferences storage for tracking completed transactions.
- Scheduled data synchronization every 5 seconds.

---

## Architecture

The app follows a combination of **MVI** (Model-View-Intent) and **MVVM** (Model-View-ViewModel) patterns to ensure separation of concerns, scalability, and testability. The architecture incorporates the following layers:

- **View**:  
  Responsible for displaying the UI and capturing user inputs.
   - `ConvertorScreen` Composable for rendering the UI.
   - `MainActivity` acts as the host for the UI components.

- **ViewModel**:  
  Handles state management, business logic, and provides data to the UI.
   - `ConvertorViewModel`: Manages UI state and invokes use cases.

- **Domain Layer**:  
  Contains core business logic.
   - Use cases like `CalculateConversionUseCase` encapsulate specific business operations.
   - **Repositories**: `CurrencyRepository`

- **Data Layer**:  
  Handles data operations, including local and remote sources.
   - **Local Storage**: `RoomDatabase` is used for storing user balances.
   - **Preferences**: `PreferencesManager` handles app-specific data persistence.
   - **RepositoriesImpl**: `CurrencyRepositoryImpl`

---

## Dependencies

The app uses the following major libraries:

- **Android Jetpack**: ViewModel, LiveData, Navigation Component
- **Compose UI**: Jetpack Compose for building the UI
- **Hilt**: Dependency Injection
- **Room**: Local database for user balances
- **Retrofit**: Network library for API calls
- **OkHttp**: For network request timeout management
- **Coroutines**: For asynchronous operations

---

## Testing Environment

- Device: **Google Pixel 8**
- Operating System: **Android 15**

---

## Future Improvements

- **Better picker for currencies**: Enhance the user experience with an improved UI/UX for selecting currencies.
- **Remembering last currencies**: Automatically prefill the last used currencies for quicker conversions.
- **Transaction history**: Add a feature to view detailed transaction history.
- **Implement dark mode**: Add support for a system-wide dark theme.

---
