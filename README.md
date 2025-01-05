# The Searcher

The Searcher is a Java-based search platform application built with Android Studio. It is designed to help users search for books and movies/shows, save their favorites, and manage their personalized accounts. The app integrates Firebase Realtime Database for backend services and uses external APIs like Google Books API and OMDb API for fetching book and movie details.

View Play Demo here (hosted on Appetize.io): https://appetize.io/app/b_gmqe24sxhfileu2ti7ni2oun2y?device=pixel7&osVersion=13.0
---

## Features

### 1. **Search for Books**
- Users can select **Book Search** from the menu (three dots at the top-right corner).
- Search for books by title.
- Get a list of close matches displayed in a scrollable **RecyclerView**.
- Click on a book to view more details.
- Option to **Add to Favorites** for quick access later.

### 2. **Search for Movies/Shows**
- Users can select **Movie/Show Search** from the menu.
- Search for movies and tv-shows by title.
- Get the information of the closest match. 
- Option to **Add to Favorites** for quick access later.

### 3. **Favorites Section**
- Access your personalized **Favorites** for both books and movies.
- Swipe left or right to delete any favorite item from your list.
- Click **Buy/Watch on Amazon** button to to visit a purchase or watch page.
  
### 4. **Profile Management**
- On the **Profile Page**, users can:
  - Edit their account profile information.
  - Reset their password.
  - Delete their account.
  - Logout of their account.

---

## Backend

### **Firebase Realtime Database**
- Firebase is used to store:
  - User login information.
  - Personalized user data, such as favorites for books and movies.

---

## APIs Used

### **Google Books API**
- Fetches book information, such as titles, authors, and descriptions.
- Provides links to purchase books online.

### **OMDb API**
- Fetches movie/show information, including titles, release years, and summaries.

---

## Key Technologies

- **Android Studio**: Development environment.
- **Java**: Programming language for app development.
- **Firebase**: Backend for authentication and real-time database.
- **Google Books API**: For book search functionality.
- **OMDb API**: For movie search functionality.

---

## How to Use

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/Searcher.git

2. Open the project in Android Studio.
3. Set up Firebase for your project:
  - Add the google-services.json file to the app directory.
  - Configure your Firebase Realtime Database.
4. Obtain API keys:
  - Google Books API Key
  - OMDb API Key
  - Add these keys to your strings.xml file or edit them in the Search Java file.
5. Build and run the app on your emulator or device.

### Notes
Dependencies: The app uses specific versions of dependencies that have been tested for compatibility. Avoid updating them unless necessary, as newer versions may cause breaking changes.

SDK Versions: Leave the SDK versions as they are in the project to avoid compatibility issues.

