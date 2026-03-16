# 🚗 Car Launcher — Lenovo M11 2025

Launcher HUD para uso no carro. Estilo painel futurista com apps de navegação, media, comunicação e viagens.

## Apps incluídos
| App | Package |
|-----|---------|
| Waze | com.waze |
| Google Maps | com.google.android.apps.maps |
| Spotify | com.spotify.music |
| YouTube | com.google.android.youtube |
| Telefone | (dialer nativo) |
| Contactos | (contacts nativo) |
| TripRank | com.triprank / com.triprank.app |

---

## ✅ Como compilar e instalar

### Pré-requisitos
- **Android Studio** (Hedgehog 2023.1 ou superior) → https://developer.android.com/studio
- **JDK 17** (normalmente já incluído no Android Studio)
- Tablet Lenovo M11 com **USB Debugging ativo**

### Passo 1 — Abrir o projeto
1. Abre o Android Studio
2. `File → Open` → seleciona a pasta `CarLauncher`
3. Aguarda o Gradle sync terminar (pode demorar 2-3 min na 1ª vez)

### Passo 2 — Compilar o APK
**Opção A — Debug (mais fácil, para testar):**
- Menu: `Build → Build Bundle(s) / APK(s) → Build APK(s)`
- O APK fica em: `app/build/outputs/apk/debug/app-debug.apk`

**Opção B — Via terminal:**
```bash
cd CarLauncher
./gradlew assembleDebug
```

### Passo 3 — Instalar no tablet
**Via USB:**
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

**Via ficheiro (sem PC):**
1. Copia o APK para o tablet
2. Abre com um gestor de ficheiros
3. Instala (precisa de "Fontes desconhecidas" ativo em Definições)

### Passo 4 — Definir como Launcher padrão
1. Carrega no botão Home do tablet
2. Aparece o menu "Selecionar launcher"
3. Escolhe **Car Launcher** → "Sempre"

---

## 🎨 Design

- Fonte: **Orbitron** (Google Fonts, carregada automaticamente)
- Paleta: Ciano néon `#00E5FF` em fundo escuro `#050A10`
- Layout em modo **paisagem forçado**
- Botão Home do Android volta sempre para este launcher

---

## 🔧 Personalizar o TripRank

Se o package name do TripRank for diferente, edita em `MainActivity.kt`:
```kotlin
private fun launchTripRank() {
    val packages = listOf("com.triprank", "com.triprank.app", "triprank.app")
    // Adiciona o package correto aqui
}
```

Para saber o package name de qualquer app instalado:
```bash
adb shell pm list packages | grep -i trip
```

---

## 📱 Compatibilidade
- Android 8.0+ (API 26)
- Testado para Lenovo M11 2025 (landscape 1920×1200)
- `android:screenOrientation="landscape"` forçado no Manifest
