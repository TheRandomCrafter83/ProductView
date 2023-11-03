package com.coderzf1.myapplication

import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.text.NumberFormat
import android.icu.util.Currency
import android.icu.util.ULocale
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductView(
    product: Product
){
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        if(product.images.isEmpty()){
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty2),
                    contentDescription = stringResource(R.string.empty_list),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }

        } else {
        HorizontalPager(
            pageCount = product.images.count(),
            modifier = Modifier
                .weight(1f),
            state = pagerState
        ) {index ->

                Column {
                    Image(
                        painter = product.images[index],
                        contentDescription = product.title + index
                    )
                    Text(
                        text = "${index + 1}/${product.images.count()}",
                        modifier = Modifier
                            .background(Color.LightGray)
                            .clip(
                                RoundedCornerShape(500.dp)
                            ),
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            formatCurrency(product.salePrice),
            color = Color.Red,
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Original Price: ${formatCurrency(product.price)}"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = product.title,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = product.description
        )
    }
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
@Preview
fun DoPreview(){
    ProductView(
        product = Product(
            title ="Test Product",
            price = 1.25,
            salePrice = 1.00,
            description = "Test description",
            images = emptyList()
        )
    )
}



@RequiresApi(Build.VERSION_CODES.R)
fun formatCurrency(currencyAmount:Double):String{
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.maximumFractionDigits = 2
    formatter.currency = Currency.getInstance(Locale.getDefault())
    return formatter.format(currencyAmount)

}

data class Product(
    val title:String,
    val price:Double,
    val salePrice:Double,
    val description:String,
    val images:List<Painter>
)