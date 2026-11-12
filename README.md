# ☕ Brewkery — Coffee Ordering Android App

Brewkery is a native Android coffee and bakery ordering application built with **Kotlin and Jetpack Compose**. The app allows users to browse menu items, search and filter products, customize their orders, manage their cart, calculate checkout totals, and place an order with a generated order ticket.

The project was developed as an Android implementation of the provided Brewkery prototype and API specification.

---

## 📱 Features

### 🏠 Home / Menu
- Browse available coffee and bakery products
- Category-based filtering
- Search products by name or tagline
- Product cards with:
  - Product image
  - Product name
  - Rating and reviews
  - Price
  - Bestseller / staff-pick information
- Cart item count
- Store information and announcement
- Loading and error states

### ☕ Product Details
- View detailed product information
- Product image and description
- Ingredients
- Size selection
- Milk/spread customization
- Sugar-level selection
- Quantity controls
- Dynamic customization pricing
- Add customized product to cart

### 🛒 Cart
- View selected products
- Increase/decrease quantity
- Remove items
- Automatic subtotal calculation
- Delivery fee calculation
- 8% tax calculation
- Final order total
- Place Order functionality

### 📦 Order Status
- Automatically generated order ticket ID
- Order confirmation
- Brewing/preparing status
- Estimated waiting time
- Display ordered items
- Return to menu

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| Kotlin | Primary programming language |
| Jetpack Compose | UI development |
| Material 3 | UI components and styling |
| MVVM | Application architecture |
| StateFlow | Reactive UI state management |
| Coroutines | Asynchronous operations |
| Retrofit | REST API communication |
| Gson | JSON serialization/deserialization |
| Coil | Remote image loading |
| Navigation Compose | Screen navigation |
| JUnit | Unit testing |
| Gradle | Build system |

---

## 🏗️ Architecture

The application follows the **MVVM architecture** with a repository layer.

```text
┌──────────────────────────────┐
│       Jetpack Compose UI     │
│                              │
│ Home / Detail / Cart / Order │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│          ViewModel           │
│                              │
│ StateFlow / UI State         │
│ Cart & Order Management      │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│         Repository           │
│                              │
│ Data handling & API calls    │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│      Retrofit API Layer      │
│                              │
│ REST API / JSON responses    │
└──────────────────────────────┘
```

### Architecture Flow

```text
Compose UI
    ↓
ViewModel
    ↓
Repository
    ↓
Retrofit
    ↓
REST API
```

UI state is exposed using `StateFlow`, allowing Compose screens to react automatically to state changes.

---

## 📂 Project Structure

```text
com.example.brewkery
│
├── MainActivity.kt
│
├── data
│   ├── model
│   │   └── Models.kt
│   │
│   ├── remote
│   │   ├── BrewkeryApi.kt
│   │   └── RetrofitInstance.kt
│   │
│   └── repository
│       └── BrewkeryRepository.kt
│
└── ui
    │
    ├── components
    │   ├── CartItemRow.kt
    │   ├── CartSummaryBar.kt
    │   ├── CategoryChip.kt
    │   ├── ErrorView.kt
    │   ├── Header.kt
    │   ├── OptionItem.kt
    │   ├── OptionSection.kt
    │   ├── ProductCard.kt
    │   └── StoreInfoCard.kt
    │
    ├── screens
    │   ├── AppViewModel.kt
    │   ├── HomeScreen.kt
    │   ├── DetailScreen.kt
    │   ├── CartScreen.kt
    │   └── OrderScreen.kt
    │
    └── theme
        └── Theme.kt
```

The project was refactored from a large activity implementation into separate screens and reusable UI components while preserving the application's existing state and business logic.

---

## 🌐 API Integration

Brewkery uses the provided REST API for menu and product information.

### Menu API

```text
https://raw.githubusercontent.com/VivekShah138/Brewkery/main/data.json
```

### Product Details API

```text
https://raw.githubusercontent.com/VivekShah138/Brewkery/main/api/items/{id}.json
```

Retrofit is used for HTTP communication, while Gson handles JSON parsing.

The application handles:

- API loading states
- Successful responses
- API failures
- Empty/error states

---

## 🛒 Cart Calculation

The cart automatically calculates the order summary.

### Subtotal

```text
Subtotal = Sum of all cart item line totals
```

### Delivery

```text
Delivery Fee = $2.50
```

when the cart contains items.

### Tax

```text
Tax = Subtotal × 8%
```

### Total

```text
Total = Subtotal + Delivery Fee + Tax
```

For example:

```text
Subtotal       $9.40
Delivery       $2.50
Tax (8%)       $0.75
----------------------
Total          $12.65
```

---

## 🔄 State Management

The application uses a centralized `AppUiState` managed by `AppViewModel`.

The state handles:

- Menu loading status
- Selected category
- Search query
- Cart items
- Product details
- Product loading/error states
- Order ticket
- Checkout calculations

`StateFlow` is used to expose state to Compose UI.

Example flow:

```text
User Action
     ↓
ViewModel
     ↓
MutableStateFlow
     ↓
StateFlow
     ↓
Compose recomposition
     ↓
Updated UI
```

---

## ⚠️ Error & Loading Handling

The application provides user feedback for asynchronous operations.

### Loading
While menu or product information is being retrieved, the UI displays an appropriate loading state.

### Error
If an API request fails, an error state is displayed instead of leaving the screen blank.

### Empty Results
Search and category filtering are handled without crashing when no matching products are available.

---

## 🧪 Testing

JUnit is included for unit testing.

The primary testing focus is the application's business logic, particularly:

- Cart calculations
- Tax calculation
- Delivery fee calculation
- Total calculation
- Quantity handling

Additional UI and integration tests can be added as the project evolves.

> Note: Only tests that are actually implemented in the repository should be listed as completed tests.

---

## 🚀 Getting Started

### Prerequisites

- Android Studio
- JDK 17
- Android SDK 35
- Android device or emulator
- Internet connection for API requests

### Clone the Repository

```bash
git clone https://github.com/ARPITSINGH088/Brewkery.git
```

### Open in Android Studio

1. Open Android Studio.
2. Select **Open**.
3. Select the cloned `Brewkery` directory.
4. Allow Gradle synchronization to complete.
5. Select an Android emulator or connected device.
6. Click **Run ▶**.

---

## 🔐 Configuration

No API key is required for the provided Brewkery API endpoints.

Internet access is required because menu and product information is retrieved from remote JSON endpoints.

---

## 🤖 AI Usage

AI tools  like ChatGPT and Claude were used during development as a productivity and development-assistance tool .

### Prompts / Tasks Used

#### 1. Project Planning

> Analyze the Brewkery Android assignment and convert the prototype and API requirements into an implementation plan using Kotlin, Jetpack Compose, MVVM, Retrofit, and Coroutines.

This helped define the application architecture, screen flow, data layer, and implementation order.

#### 2. API & Data Models

> Generate Kotlin data models and Retrofit API interfaces based on the provided Brewkery JSON structure and API endpoints.

This accelerated the initial API integration and model creation.

#### 3. Compose UI Review

> Review the Jetpack Compose implementation against the Brewkery requirements and identify missing UI states, architectural issues, and potential bugs.

This was used to review the implementation against the prototype requirements.

#### 4. Refactoring

> Refactor the large MainActivity.kt into separate screen and reusable component files while preserving the existing functionality and application logic.

This helped break the large activity into:

- Screens
- Reusable components
- ViewModel
- Data layer
- Theme

#### 5. Debugging

> Analyze Kotlin/Compose compilation errors caused by moving screens and components into different packages and identify the required package and import changes without changing the application's logic.

This was used during the refactoring process to resolve package and import issues.

---

Actual Prompts Used -

1. “Analyze the Brewkery assignment and convert the prototype, API requirements, and implementation guidelines into a structured Android development plan using Kotlin, Jetpack Compose, MVVM, Retrofit, and Coroutines.”

2. “Generate Kotlin data models and Retrofit API interfaces based on the provided Brewkery JSON structure and API endpoints.”

3. “Review the current Brewkery Jetpack Compose implementation against the assignment requirements and identify UI issues, missing states, architectural problems, and potential bugs.”

4. “Help refactor the 2000+ line MainActivity.kt into separate screen and reusable component files while preserving the existing functionality and application logic.”

5. “Debug the Kotlin/Jetpack Compose compilation errors caused by moving screens and components into different packages, and identify the required package and import changes without changing the application logic.”



## 🧠 What AI Got Right

AI was particularly useful for:

- Initial project planning
- API model generation
- Retrofit setup
- Compose scaffolding
- State management ideas
- Loading and error handling
- Cart calculation logic
- Identifying edge cases
- Large-scale code refactoring
- Debugging package/import issues

---

## 🐛 What AI Got Wrong

Some AI-generated suggestions initially introduced unnecessary architectural complexity.

For example, an initial refactoring approach suggested splitting the shared AppViewModel into multiple screen-specific ViewModels. This could have unnecessarily changed the existing state-management approach because cart and order information are shared across screens.

AI-generated code also required manual correction during the package refactoring process because moving composables into different packages caused outdated imports and references to the original MainActivity.kt package.



### How It Was Fixed

The implementation was reviewed manually and the architecture was kept around a shared `AppViewModel` for application-level state.

The refactoring was performed incrementally:

```text
Original MainActivity
        ↓
Separate Screens
        ↓
Reusable Components
        ↓
Updated Packages & Imports
        ↓
Compile & Debug
```

The core application behavior and business logic were preserved during this process.

---

## 🔮 Future Improvements

Possible improvements include:

- Persistent cart using Room
- User authentication
- Real payment gateway integration
- Order history
- Push notifications
- Real-time order tracking
- Offline caching
- Improved automated UI testing
- Backend integration for real orders
- User profile and address management

---

## 📸 Screens

The application includes the following primary screens:

1. Home / Menu
2. Product Details
3. Cart
4. Order Status

---

## 👨‍💻 Author

**Arpit Singh Kushwah**

Android Developer | Kotlin | Jetpack Compose | MVVM

---

## 📄 License

This project was created for educational, development, and assignment purposes.
