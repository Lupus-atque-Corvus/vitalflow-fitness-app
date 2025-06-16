# VitalFlow - Modular Fitness App

VitalFlow is a modern, modular Android fitness application built with Jetpack Compose and Material Design 3. The app features a Frutiger Aero-inspired design with glassy, aquatic elements and supports multiple languages.

## Features

### Training Module
- **Gym Training**
  - Custom exercise management
  - Training plan generation (Full Body, Upper/Lower, Push/Pull/Legs)
  - Set and weight tracking
  - Automatic progression calculation

- **Outdoor Activities**
  - Running tracking with distance, time, and pace
  - Historical run data
  - Performance statistics

- **Stretching**
  - Pre-defined stretching exercises
  - Custom exercise creation
  - Visual guides with images
  - Duration tracking

- **Supplements**
  - Supplement tracking
  - Dosage management
  - Intake scheduling
  - Smart reminders

### Technical Features
- Modern UI with Jetpack Compose and Material Design 3
- Frutiger Aero-inspired design system
- Multi-language support (English, German, Russian)
- Dark/Light theme support
- Modular architecture for easy expansion
- Clean Architecture principles

## Technology Stack

- **UI Framework:** Jetpack Compose with Material Design 3
- **Architecture:** Clean Architecture with MVVM
- **Dependency Injection:** Hilt
- **Navigation:** Jetpack Navigation Compose
- **Image Loading:** Coil
- **Concurrency:** Kotlin Coroutines
- **Background Processing:** WorkManager
- **Health Integration:** Health Connect / Google Fit

## Design

The app features a modern, clean design inspired by the Frutiger Aero aesthetic:
- Glassy, translucent surfaces
- Aquatic-inspired animations
- Light and airy layouts
- Smooth transitions
- Responsive design for all screen sizes

## Modular Structure

```
VitalFlow/
├── Training/
│   ├── Gym/
│   │   ├── Training Plans
│   │   ├── Exercises
│   │   └── Progress Tracking
│   ├── Outdoor/
│   │   └── Running
│   ├── Stretching/
│   │   └── Exercise Library
│   └── Supplements/
│       └── Tracking
├── Settings/
│   ├── Language
│   └── Preferences
└── Placeholders for future modules
```

## Language Support

- English (default)
- German (Deutsch)
- Russian (Русский)
- Automatic system language detection
- Fallback to English

## Getting Started

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on an emulator or device (minimum Android 10, API 30)

## Future Enhancements

- WearOS support
- Additional training modules
- Advanced analytics
- Social features
- Cloud sync
- More language support

## Contributing

Contributions are welcome! Please feel free to submit pull requests.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
