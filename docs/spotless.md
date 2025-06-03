# Spotless Code Formatting

This project uses [Spotless](https://github.com/diffplug/spotless) for code formatting and style enforcement. Spotless
helps maintain consistent code style across the project.

## Configuration

The Spotless configuration is defined in the `spotless.gradle` file in the project root. This configuration is applied
to all subprojects.

### Java Formatting Rules

- Uses Google's Java style guide
- Removes unused imports
- Trims trailing whitespace
- Ensures files end with a newline
- Enforces import order: java, javax, org, com, io, others
- Prevents wildcard imports
- Adds license header to all Java files

### Other Formatting Rules

- Gradle files: Uses greclipse formatter with 4-space indentation
- XML files: Uses 4-space indentation
- Markdown files: Trims trailing whitespace and ensures files end with a newline

## Usage

### Check Code Formatting

To check if your code follows the formatting rules without making any changes:

```bash
./gradlew spotlessCheck
```

This will report any formatting violations without modifying the files.

### Apply Code Formatting

To automatically format your code according to the rules:

```bash
./gradlew spotlessApply
```

This will reformat all files to comply with the formatting rules.

### Integration with Build Process

The Spotless check is integrated with the build process. If you run:

```bash
./gradlew build
```

It will automatically run `spotlessCheck` as part of the build process. If there are any formatting violations, the
build will fail.

## IDE Integration

For the best development experience, configure your IDE to use the same formatting rules as Spotless:

### IntelliJ IDEA

1. Install the [google-java-format plugin](https://plugins.jetbrains.com/plugin/8527-google-java-format)
2. Enable the plugin in Settings → Other Settings → google-java-format Settings
3. Select "Enable google-java-format" and "Use the reformatter even if a file is not formatted with it"

### Eclipse

1. Download the [google-java-format Eclipse plugin](https://github.com/google/google-java-format/releases)
2. Place the JAR file in Eclipse's `dropins` folder
3. Restart Eclipse
4. Configure the formatter in Window → Preferences → Java → Code Style → Formatter

### VS Code

1. Install the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
2. Install
   the [Google Java Format extension](https://marketplace.visualstudio.com/items?itemName=mngrm3a.vscode-google-java-formatter)
3. Configure the extension in settings.json

## Best Practices

- Run `spotlessApply` before committing code
- Configure your IDE to use the same formatting rules
- Don't manually format code in ways that conflict with Spotless rules
- If you need to exclude specific files from formatting, add them to the `targetExclude` list in the Spotless
  configuration
