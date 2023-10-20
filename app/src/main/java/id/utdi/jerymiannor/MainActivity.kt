package id.utdi.jerymiannor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.utdi.jerymiannor.ui.theme.BookKuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookKuTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentIndex = 0
                    BookApp(artItems[currentIndex])
                }
            }
        }
    }
}

data class ArtItem(val imageResId: Int, val name: String, val year: String) //TODO: ArtItem adalah data class yang digunakan untuk merepresentasikan item buku yang ada dalam daftar.

val artItems = listOf( //TODO: artItems adalah daftar item buku yang akan ditampilkan dalam aplikasi. Setiap item terdiri dari gambar, judul, dan tahun.
    ArtItem(R.drawable.book1, "Book 1: Macbeth - William Shakespeare", "2017"),
    ArtItem(R.drawable.book2, "Book 2: Hujan Bulan Juni - Sapardi Djoko Damono", "2015"),
    ArtItem(R.drawable.book3, "Book 3: Bridge To Terabithia - Katherine Paterson", "2017"),
)

@Composable
fun BookApp(artItem: ArtItem) { //TODO: BookApp adalah komposisi utama untuk menampilkan item. currentIndex adalah indeks buku yang sedang ditampilkan saat ini. focusManager untuk mengelola fokus dalam UI. Dan isDescriptionVisible adalah status yang menentukan apakah deskripsi buku ditampilkan atau disembunyikan.
    var currentIndex by remember { mutableStateOf(0) }
    val focusManager = LocalFocusManager.current
    var isDescriptionVisible by remember { mutableStateOf(false) }

    LazyColumn( //TODO: LazyColumn digunakan agar aplikasi dapat discroll ketika ada tampilan yang melewati batas layar.
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {// TODO: item adalah elemen dalam LazyColumn yang digunakan untuk menambahkan komponen ke dalam daftar item.
            val currentItem = artItems[currentIndex]
            Image( //TODO: Image adalah komponen Compose untuk menampilkan gambar buku saat ini.
                painter = painterResource(id = currentItem.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Row( //TODO: Row adalah baris yang berisi tombol "Previous" dan "Next" untuk melihat buku-buku yang ada.
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button( //TODO: Ketiga Button digunakan untuk mengganti buku ke selanjutnya atau sebelumnya dan menampilkan/menyembunyikan deskripsi buku ketika diklik.
                    onClick = {
                        currentIndex = (currentIndex - 1).coerceAtLeast(0)
                        isDescriptionVisible = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Previous") //TODO: Ketiga Text disini digunakan untuk menampilkan teks "Previous", "Next", dan "More" pada button/tombol
                }
                Button(
                    onClick = {
                        currentIndex = (currentIndex + 1).coerceAtMost(artItems.size - 1)
                        isDescriptionVisible = false
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                ) {
                    Text("Next")
                }
            }
        }

        item {
            Button(
                onClick = {
                    isDescriptionVisible = !isDescriptionVisible
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(if (isDescriptionVisible) "Hide Description" else "More")
            }
        }

        if (isDescriptionVisible) { //TODO: if disini, jika deskripsi terlihat dengan menekan tombol more maka akan menampilkan deskripsi sesuai dengan gambar buku saat ini.
            item {
                val currentItem = artItems[currentIndex]
                val bookDescription = when (currentIndex) { //TODO: When digunakan untuk menentukan deskripsi buku berdasarkan currentIndex. 0 untuk buku pertama, 1 untuk buku kedua, dan 2 untuk buku ketiga.
                    0 -> "  Macbeth adalah sebuah drama tragedi karya William Shakespeare. Ini adalah salah satu karya paling terkenal dalam sastra Inggris.Kisah ini mendramatisir efek psikologis yang diakibatkan oleh orang yang memiliki ambisi besar untuk berkuasa demi kepentingan pribadinya."
                    1 -> "  Hujan Bulan Juni adalah sebuah kumpulan puisi karya Sapardi Djoko Damono. Puisi-puisinya sangat indah dan mendalam. Novel Hujan Bulan Juni juga berisi tentang kisah percintaan Sarwono dan Pingkan, berisi manis pahitnya hubungan keduanya."
                    2 -> "  Bridge To Terabithia adalah sebuah novel anak-anak karya Katherine Paterson. Novel ini bercerita tentang siswa kelas lima Jesse Aarons , yang berteman dengan tetangga barunya, Leslie Burke , setelah dia kalah dalam perlombaan lari dengannya di sekolah. Leslie adalah seorang tomboi dari keluarga kaya, dan Jesse sangat menghargainya. Jesse adalah anak laki-laki artistik dari keluarga miskin yang, pada awalnya, penakut dan marah. Namun, setelah bertemu Leslie, hidupnya berubah. Dia menjadi berani dan belajar melepaskan rasa frustrasinya. Kedua anak itu menciptakan sebuah kerajaan untuk diri mereka sendiri, yang oleh Leslie diberi nama Terabithia."
                    else -> "Deskripsi buku tidak tersedia."
                }
                val bookDescriptionText = "Book:\n${currentItem.name} - Year: ${currentItem.year}.\n\nDeskripsi:\n$bookDescription"
                Text(
                    text = bookDescriptionText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify
                )
            }
        }
    }
}

@Composable
@Preview
fun BookAppPreview() { //TODO: BookAppPreview digunakan untuk menampilkan preview UI dan layout dari aplikasi yang dibuat.
    val artItems = listOf(
        ArtItem(R.drawable.book1, "Book 1: Macbeth - William Shakespeare", "2017"),
        ArtItem(R.drawable.book2, "Book 2: Hujan Bulan Juni - Sapardi Djoko Damono", "2013"),
        ArtItem(R.drawable.book3, "Book 3: Bridge To Terabithia - Katherine Paterson", "2017"),
    )

    var currentIndex by remember { mutableStateOf(0) }

    BookApp(artItems[currentIndex])
}