# PPB-6-CurrencyCalculator
 


## MainActivity.kt
File utama yang berisi logic konversi mata uang

### Fungsi Utama:
```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                Surface {
                    CurrencyConverterScreen()
                }
            }
        }
    }
}
```
- `onCreate()`: Fungsi pertama yang dijalankan saat app dibuka
- `setContent`: Menentukan tampilan UI menggunakan Jetpack Compose
- `CurrencyConverterTheme`: Menggunakan tema custom dari aplikasi
- `Surface`: Container dasar untuk layout

## CurrencyConverterScreen()
Composable utama untuk tampilan konverter

```kotlin
@Composable
fun CurrencyConverterScreen() {
    // State management
    var idrAmount by remember { mutableStateOf("") }
    var selectedCurrency by remember { mutableStateOf("USD") }
    
    // Data konversi
    val conversionRates = mapOf(
        "USD" to 0.000059,
        "GBP" to 0.000045,
        "EUR" to 0.00005225239,
        "JPY" to 0.0084
    )
    
    // Hitung hasil konversi
    val convertedAmount = idrAmount.toDoubleOrNull()?.let {
        it * (conversionRates[selectedCurrency] ?: 0.0)
    } ?: 0.0
}
```
- `idrAmount`: Menyimpan input jumlah uang dalam IDR
- `selectedCurrency`: Menyimpan mata uang yang dipilih
- `conversionRates`: Map berisi rate konversi IDR ke mata uang lain
- `convertedAmount`: Hasil perhitungan konversi mata uang

### Layout Utama:
```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    // Komponen UI
}
```
- Menggunakan `Column` untuk tata letak vertikal
- `padding(16.dp)`: Jarak dari tepi layar
- `spacedBy(16.dp)`: Jarak antar komponen

### Komponen UI:
1. **Input Amount**:
```kotlin
OutlinedTextField(
    value = idrAmount,
    onValueChange = { idrAmount = it },
    label = { Text("Amount in IDR") },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Done
    )
)
```
- Input text dengan outline
- Hanya menerima input angka (`KeyboardType.Number`)
- Tombol keyboard berubah jadi "Done" saat input

2. **Currency Dropdown**:
```kotlin
CurrencyDropdown(
    selectedCurrency = selectedCurrency,
    onCurrencySelected = { selectedCurrency = it }
)
```
- Komponen dropdown untuk pilih mata uang
- Akan memanggil callback saat mata uang berubah

3. **Hasil Konversi**:
```kotlin
Text(
    text = "Converted: $convertedAmount $selectedCurrency",
    style = MaterialTheme.typography.bodyLarge
)
```
- Menampilkan hasil konversi dalam format: "Converted: [jumlah] [mata uang]"

## CurrencyDropdown()
Composable untuk dropdown pilihan mata uang

```kotlin
@Composable
fun CurrencyDropdown(selectedCurrency: String, onCurrencySelected: (String) -> Unit) {
    val currencies = listOf("USD", "GBP", "EUR", "JPY")
    var expanded by remember { mutableStateOf(false) }
    
    Box {
        // Tombol trigger dropdown
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCurrency)
        }
        
        // Menu dropdown
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = { Text(currency) },
                    onClick = {
                        onCurrencySelected(currency)
                        expanded = false
                    }
                )
            }
        }
    }
}
```
- `expanded`: State untuk menampilkan/sembunyikan dropdown
- `OutlinedButton`: Tombol yang menampilkan mata uang terpilih
- `DropdownMenu`: Daftar pilihan mata uang
- `DropdownMenuItem`: Item individual dalam dropdown

## Flow Aplikasi
1. User input jumlah IDR
2. Pilih mata uang tujuan
3. Aplikasi hitung konversi secara real-time
4. Hasil ditampilkan di bawah

## Catatan:
- Rate konversi hardcoded (sebaiknya diambil dari API)
- Tidak ada validasi input (bisa crash kalau input bukan angka)
- UI sederhana bisa dikembangkan lebih lanjut
