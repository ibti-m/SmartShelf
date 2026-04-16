# SmartShelf 

A food waste tracking Android app that helps users reduce food waste by monitoring what's in their kitchen, highlighting items nearing expiration, and suggesting recipes to use them up. Built as an HCI prototype with Kotlin + XML layouts.

Here is the link to our demo video: https://youtu.be/in0N0RFAiic

---

## Setup Instructions

### Prerequisites

1. **Android Studio**: Download and install the latest version from [developer.android.com/studio](https://developer.android.com/studio).
2. **JDK 17**: Android Studio bundles its own JBR (JetBrains Runtime). No separate JDK installation is needed, but if you build from the command line, set `JAVA_HOME` to Android Studio's bundled JBR:
   ```
   # Windows (PowerShell)
   $env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
   ```
3. **Android SDK 34**: Open *Android Studio* > *Settings* > *SDK Manager*, and ensure the **Android 14.0 (API 34)** SDK Platform is installed.

### Emulator Setup

1. In Android Studio, open *Device Manager* (right sidebar or *Tools* > *Device Manager*).
2. Click **Create Virtual Device**.
3. Select **Pixel 8** (or any phone profile).
4. Choose the system image **API 35** (or API 34+) and click *Download* if needed.
5. Launch the emulator device.

> The demo was run on a **Pixel 8 emulator, API 35**.

### Running the App

1. Clone the repository:
   ```
   git clone <repo-url>
   cd SmartShelf
   ```
2. Open the project in Android Studio (*File* > *Open* and select the `SmartShelf` folder).
3. Wait for Gradle sync to complete (or sync manually).
4. Select your Pixel 8 emulator (or a connected device) from the device dropdown.
5. Click **Run ▶** (or press `Shift + F10`).

To build from the command line:
```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
.\gradlew.bat assembleDebug
```

---

## Features

Please refer to the YouTube demo video for an overview of all features: https://youtu.be/in0N0RFAiic

### Home - Expiring Soon
- Displays all food items that are expired or expiring within 3 days, sorted by nearest expiry date.
- Each card shows the item name, category badge, quantity, and a color-coded expiry label (red for expired, orange for expiring soon).
- **Delete** removes the item from inventory.
- **To Shopping** moves the item to the shopping list so you remember to re-buy it, then removes it from inventory.
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/b61cb83b-c157-4aab-a200-19c2d6a43d2a" />

### Inventory
- Full list of all stored food items, sorted by expiry date (nearest first).
- Each card shows name, category, quantity, and expiry status (red/orange/green).
- Tap a card to **edit** the item's details.
- **Delete** removes the item; **To Shopping** moves it to the shopping list.
- **Add Item** button opens the Add/Edit form to create a new entry.
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/b06c4388-088e-4c8c-95aa-d969e10dfe44" />

### Add / Edit Item
- Form with fields for item name, quantity (+/− selector), expiry date (date picker), and category (dropdown).
- Subheader: "Enter item details below."
- **Quick Scan** section at the bottom with a barcode graphic (non-functional prototype placeholder — in a full version this would open the camera to auto-fill item details).
- When accessed via the shopping list's "Bought" button, the name is pre-filled and the item is automatically removed from the shopping list on save.
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/9004160b-072b-4a0f-8c2a-993ef86d8c3a" />
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/34c36d4d-a2cc-43e9-8490-809bbc64e979" />

### Shopping List
- Add items by typing a name and tapping **Add**.
- Each item has a **Bought** button that opens the Add/Edit form with the name pre-filled, letting you set quantity, expiry, and category before saving to inventory. On save the item is removed from the shopping list; cancelling keeps it.
- **Delete** removes an item from the list immediately.
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/632754b2-ff63-4b0c-b9d1-7aaf42b234f1" />

### Recipes
- Displays a list of suggested recipe cards, each with a title, short description, and tag pills (e.g. "Quick", "Vegetarian", "Healthy").
- Tapping a recipe opens a **detail screen** with the full bulleted ingredient list and numbered preparation steps.
- A green back-arrow button returns to the recipe list.
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/3ec639b5-b559-4981-b05f-d5f0ef669c12" />
<img width="239" height="489" alt="image" src="https://github.com/user-attachments/assets/5ab13982-ec17-4d86-9299-1949b2501303" />

