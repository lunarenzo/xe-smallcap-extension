# xe-smallcap-extension

**Small Caps Text Converter Extension for Xed-Editor**

An extension for [Xed-Editor](https://github.com/Xed-Editor/Xed-Editor) that provides Unicode Small Caps formatting for highlighted text and real-time typing mode.

---

## ✨ Features

- **Text Selection Conversion**: Highlight any text in the editor (e.g. `Sword` or `sword`) and tap the `ѕᴡᴏʀᴅ (Small Caps)` action button on the editor toolbar to transform it instantly into `ѕᴡᴏʀᴅ`.
- **Small Caps Input Mode Toggle**: Toggle Small Caps Input mode on/off to convert text typing on the fly.
- **Command Palette Integration**: Commands are available in the Command Palette and can be bound to custom keyboard shortcuts.

---

## 🛠️ Building via GitHub Actions

This repository includes an automated GitHub Actions CI workflow in `.github/workflows/build.yml` that builds the extension package (`.zip`) on every push and release.

### Manual Local / Docker Build

```bash
./gradlew shadowJar buildExtensionPackage
```

Output `.zip` file will be generated in `output/com.lunarenzo.smallcaps.zip`.

---

## 📄 License

MIT License
