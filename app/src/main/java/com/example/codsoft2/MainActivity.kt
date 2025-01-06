package com.example.codsoft2

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.codsoft2.databinding.ActivityMainBinding
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quotes: List<Pair<String, String>> = listOf(
        "The best way to predict the future is to create it." to "Peter Drucker",
        "Happiness is not by chance, but by choice." to "Jim Rohn",
        "Start where you are. Use what you have. Do what you can." to "Arthur Ashe",
        "The harder you work for something, the greater you’ll feel when you achieve it." to "Anonymous",
        "Do something today that your future self will thank you for." to "Sean Patrick Flanery",
        "Act as if what you do makes a difference. It does." to "William James",
        "Don’t watch the clock; do what it does. Keep going." to "Sam Levenson",
        "You are never too old to set another goal or to dream a new dream." to "C.S. Lewis",
        "Believe you can and you’re halfway there." to "Theodore Roosevelt",
        "Your limitation—it’s only your imagination." to "Anonymous",
        "Dream big and dare to fail." to "Norman Vaughan",
        "Turn your wounds into wisdom." to "Oprah Winfrey",
        "Success is not the key to happiness. Happiness is the key to success." to "Albert Schweitzer",
        "It always seems impossible until it’s done." to "Nelson Mandela",
        "Don’t let yesterday take up too much of today." to "Will Rogers",
        "The secret of getting ahead is getting started." to "Mark Twain",
        "Be yourself; everyone else is already taken." to "Oscar Wilde",
        "Failure is the condiment that gives success its flavor." to "Truman Capote",
        "What you get by achieving your goals is not as important as what you become by achieving your goals." to "Zig Ziglar",
        "Challenges are what make life interesting and overcoming them is what makes life meaningful." to "Joshua J. Marine",
        "Opportunities don’t happen. You create them." to "Chris Grosser",
        "Success is walking from failure to failure with no loss of enthusiasm." to "Winston Churchill",
        "Believe in yourself and all that you are." to "Christian D. Larson",
        "Hardships often prepare ordinary people for an extraordinary destiny." to "C.S. Lewis",
        "If you’re going through hell, keep going." to "Winston Churchill",
        "The man who moves a mountain begins by carrying away small stones." to "Confucius",
        "Don’t be afraid to give up the good to go for the great." to "John D. Rockefeller",
        "Start each day with a positive thought and a grateful heart." to "Roy T. Bennett",
        "Life is 10% what happens to us and 90% how we react to it." to "Charles R. Swindoll",
        "Keep your face always toward the sunshine—and shadows will fall behind you." to "Walt Whitman",
        "Success is not final, failure is not fatal: It is the courage to continue that counts." to "Winston Churchill",
        "Perseverance is not a long race; it is many short races one after the other." to "Walter Elliot",
        "Your life does not get better by chance, it gets better by change." to "Jim Rohn",
        "Do what you feel in your heart to be right—for you’ll be criticized anyway." to "Eleanor Roosevelt",
        "Work hard in silence, let your success be your noise." to "Frank Ocean",
        "In the middle of every difficulty lies opportunity." to "Albert Einstein",
        "Life isn’t about finding yourself. It’s about creating yourself." to "George Bernard Shaw",
        "Dream as if you’ll live forever. Live as if you’ll die today." to "James Dean",
        "The only way to achieve the impossible is to believe it is possible." to "Charles Kingsleigh",
        "Courage is like a muscle. We strengthen it by use." to "Ruth Gordo",
        "If you want to fly, you have to give up the things that weigh you down." to "Toni Morrison",
        "The greatest glory in living lies not in never falling, but in rising every time we fall." to "Nelson Mandela",
        "Keep your eyes on the stars and your feet on the ground." to "Theodore Roosevelt",
        "Every day may not be good, but there’s something good in every day." to "Alice Morse Earle",
        "Do one thing every day that scares you." to "Eleanor Roosevelt",
        "Life is short, and it is up to you to make it sweet." to "Sarah Louise Delany",
        "Success usually comes to those who are too busy to be looking for it." to "Henry David Thoreau",
        "Don’t let the fear of losing be greater than the excitement of winning." to "Robert Kiyosaki",
        "Stay close to anything that makes you glad you are alive." to "Hafiz",
        "Happiness often sneaks in through a door you didn’t know you left open." to "John Barrymore",
        "Don’t wait. The time will never be just right." to "Napoleon Hill",
        "You don’t have to be great to start, but you have to start to be great." to "Zig Ziglar",
        "Success is liking yourself, liking what you do, and liking how you do it." to "Maya Angelou",
        "Go confidently in the direction of your dreams. Live the life you have imagined." to "Henry David Thoreau",
        "It’s not whether you get knocked down, it’s whether you get up." to "Vince Lombardi",
        "Your life only gets better when you get better." to "Brian Tracy",
        "The way to get started is to quit talking and begin doing." to "Walt Disney",
        "Do not wait to strike till the iron is hot; but make it hot by striking." to "William Butler Yeats",
        "Success is the sum of small efforts, repeated day-in and day-out." to "Robert Collier",
        "Motivation is what gets you started. Habit is what keeps you going." to "Jim Ryun",
        "The only limit to our realization of tomorrow will be our doubts of today." to "Franklin D. Roosevelt",
        "The future depends on what you do today." to "Mahatma Gandhi",
        "It is during our darkest moments that we must focus to see the light." to "Aristotle Onassis",
        "No matter what people tell you, words and ideas can change the world." to "Robin Williams",
        "Don’t wait for opportunity. Create it." to "George Bernard Shaw",
        "The only way to do great work is to love what you do." to "Steve Jobs",
        "Don’t limit your challenges. Challenge your limits." to "Anonymous",
        "Push yourself, because no one else is going to do it for you." to "Anonymous",
        "Failure will never overtake me if my determination to succeed is strong enough." to "Og Mandino",
        "You don’t need to see the whole staircase, just take the first step." to "Martin Luther King Jr.",
        "Great things never come from comfort zones." to "Anonymous",
        "Dream it. Wish it. Do it." to "Anonymous",
        "Success doesn’t just find you. You have to go out and get it." to "Anonymous",
        "The harder you work, the luckier you get." to "Gary Player",
        "Sometimes later becomes never. Do it now." to "Anonymous",
        "Be so good they can’t ignore you." to "Steve Martin",
        "The difference between ordinary and extraordinary is that little extra." to "Jimmy Johnson",
        "Don’t stop when you’re tired. Stop when you’re done." to "Marilyn Monroe",
        "Wake up with determination. Go to bed with satisfaction." to "Anonymous",
        "Little things make big days." to "Anonymous",
        "It’s going to be hard, but hard does not mean impossible." to "Anonymous",
        "Don’t wait for the perfect moment. Take the moment and make it perfect." to "Anonymous",
        "The key to success is to focus on goals, not obstacles." to "Anonymous",
        "Don’t wish for it. Work for it." to "Anonymous",
        "Success is not how high you have climbed, but how you make a positive difference to the world." to "Roy T. Bennett",
        Pair("إبراهيم الفقي", "إن الحياة لا تنتظر أحدًا، إذا كنت تريد شيئًا فاعمل على تحقيقه الآن."),
        Pair("جبران خليل جبران", "إذا أردت أن تعرف حقيقة الإنسان، فاسأله عن أحب الناس إليه."),
        Pair("نجيب محفوظ", "في الحياة، يجب أن نتعلم أن نغفر وأن نتنازل."),
        Pair("الرافعي", "الفكر هو الضوء الذي يرشدك إلى الطريق الصحيح."),
        Pair("طه حسين", "الكتاب هو أفضل صديق للإنسان لأنه لا يخون."),
        Pair("محمود درويش", "على هذه الأرض ما يستحق الحياة."),
        Pair("أحمد شوقي", "العقل زينة، ولكن الفضيلة هي التي تزينه."),
        Pair("أدونيس", "الشعر ليس كلمات، بل هو إحساس ومعنى."),
        Pair("يوسف إدريس", "كل إنسان يحمل في قلبه قصة يجب أن تروى."),
        Pair("محمود درويش", "علينا أن نزرع الأمل في أنفسنا مهما كانت الظروف."),
        Pair("أحمد مراد", "لا تدع الفرصة تفوتك، الحياة قصيرة جدًا."),
        Pair("نزار قباني", "الحب ليس كلمات، بل هو فعل ومعاناة."),
        Pair("غادة السمان", "كل لحظة في الحياة هي فرصة جديدة لبداية جديدة."),
        Pair("أحمد شوقي", "العقل هو أساس التقدم في الحياة."),
        Pair("إحسان عبد القدوس", "الحياة ليست سهلة، ولكن يجب أن نواجه التحديات."),
        Pair("بدر شاكر السياب", "الشعر هو التعبير عن أحاسيسنا ومشاعرنا."),
        Pair("أمينة سعيد", "النجاح ليس إلا نتيجة للاستمرار في العمل والإصرار."),
        Pair("الشيخ محمد متولي الشعراوي", "الحياة قصيرة، ولكن العمل الصالح يدوم."),
        Pair("عبد الرحمن الكواكبي", "لا تقدم رجلاً على أخرى إلا إذا كانت لديه الكفاءة."),
        Pair("محمود درويش", "الفكرة التي تحيا في ذهنك، تعيش في قلبك أيضًا."),
        Pair("خليل جبران", "الحرية ليست مجرد غياب القيود، بل هي القدرة على اختيار الطريق."),
        Pair("الشيخ زايد بن سلطان آل نهيان", "الحياة بسيطة، كلما أخذت منها، كلما أعطتك."),
        Pair("فاروق جويدة", "الحب لا يُقاس بالكلمات، بل بالأفعال."),
        Pair("الزيات", "كلما تعلمت أكثر، كلما أصبحت أقرب للنجاح."),
        Pair("يوسف القرضاوي", "الزهد في الحياة هو توازن بين الرفاهية والتضحية."),
        Pair("علي بن أبي طالب", "من أعزَّ نفسه عن الذل، رفع قدره في الدنيا."),
        Pair("ابن خلدون", "الشخص الذي يملك الرؤية هو من يستطيع أن يغير العالم."),
        Pair("ابن عربي", "لا تحاول أن تكون مثل الآخرين، بل كن نفسك."),
        Pair("محمود درويش", "الذي لا يحب الحياة، هو الذي لا يعرف كيف يقدرها."),
        Pair("جورج طرابيشي", "الحياة ليست ما تعيشه، بل ما تتذكره."),
        Pair("أم كلثوم", "الحياة ليست دائمًا مليئة بالفرح، ولكن يجب أن نواجه الحزن."),
        Pair("فؤاد التكرلي", "الشجاعة ليست في عدم الخوف، بل في مواجهة الخوف."),
        Pair("محمود درويش", "لا تبكي على شيء مفقود، بل ابتسم لأنه كان جزءًا من حياتك."),
        Pair("نزار قباني", "الحب ليس سحرًا، بل هو حقيقة مؤلمة."),
        Pair("أحمد شوقي", "الأمل هو النور الذي يضيء الطريق."),
        Pair("سامي يوسف", "الفن هو التعبير عن الروح والأحاسيس."),
        Pair("خالد بن الوليد", "القوة ليست في السلاح، بل في الإيمان."),
        Pair("أمير الشعراء أحمد شوقي", "الذين لا يحاربون يصابون بالهزيمة."),
        Pair("عبدالله بن عباس", "من طلب العز في غير الله فقد طلب الذل."),
        Pair("الطغرائي", "الحياة هي رحلة طويلة، تأخذك من مكان إلى آخر."),
        Pair("ابن سينا", "العقل هو مفتاح التقدم في الحياة."),
        Pair("الحسن بن الهيثم", "العلم هو الطريق إلى السعادة الحقيقية."),
        Pair("البردوني", "الشعر هو أداة للتعبير عن أعماق الإنسان."),
        Pair("صلاح عبد الصبور", "الأمل هو أن تجد النور في الظلام."),
        Pair("أمل دنقل", "الحياة ليس فقط للحلم، بل للعمل من أجل المستقبل."),
        Pair("أدونيس", "الشعر هو صوت الإنسان الذي يتنفس به."),
        Pair("الشيخ الشعراوي", "الحياة ليست أبدية، ولكن الأثر يبقى."),
        Pair("منيف الرزاز", "لا شيء يضاهي الحرية إلا بعد النضال من أجلها."),
        Pair("شوقي ضيف", "اللغة هي الوعاء الذي يحمل الفكر."),
        Pair("أدهم الشرقاوي", "من لا يقاوم، لا ينمو."),
        Pair("جمال عبدالناصر", "العزيمة والإرادة هي سر النجاح."),
        Pair("حسن البنا", "من يزرع اليوم، يحصد غدًا."),
        Pair("جمال الغيطاني", "الكتاب هو مرآة للحياة."),
        Pair("إدريس الشرايبي", "الفكر هو الذي يجعل الإنسان قادرًا على التغيير."),
        Pair("الطاهر بن جلون", "المعرفة هي السلاح الأقوى ضد الجهل."),
        Pair("أحمد زويل", "العلم هو السبيل الوحيد لتغيير العالم."),
        Pair("محمود درويش", "أحيانًا، لا يمكننا التحدث عن الألم، يجب أن نعيشه."),
        Pair("إلياس بوصيحة", "الفشل ليس النهاية، بل بداية جديدة."),
        Pair("نزار قباني", "الحب هو الفوضى الوحيدة التي تمنح الحياة طعمًا."),
        Pair("إبراهيم طوقان", "الصبر هو سمة من سمات الأبطال."),
        Pair("أمل دنقل", "من لا يملك حلمًا، لا يملك شيئًا."),
        Pair("سعاد الصباح", "المرأة هي القوة التي تبني المستقبل."),
        Pair("نجيب محفوظ", "المستقبل لا يتوقف، لكنه يحتاج إلى من يواجهه."),
        Pair("خليل جبران", "لا تخف من التغيير، لأنه قد يفتح أبوابًا لم تعرفها."),
        Pair("أنسي الحاج", "الشعر هو الوقود الذي يحرك العقول."),
        Pair("إدوارد سعيد", "الإنسان يجب أن يكون حرًا، لأن الحرية هي المفتاح."),
        Pair("محمد حسين هيكل", "التاريخ هو دروس الماضي لأجيال المستقبل."),
        Pair("سليم بركات", "الإنسان هو من يخلق نفسه."),
        Pair("محسن خالد", "الخيبة هي أن لا تتعلم من أخطائك."),
        Pair("محمود درويش", "الشعب لا يموت، لأنه في الأرض."),
        Pair("صادق جلال العظم", "الإنسان دائمًا ما يسعى نحو الأفضل."),
        Pair("غازي القصيبي", "التقدم هو أن نتحرك للأمام، مهما كانت الصعوبات."),
        Pair("عبد الرحمن منيف", "الكتابة هي سعي للبحث عن الحقيقة."),
        Pair("أحمد مطر", "الكلمات لا تملك قوة السيف، لكنها تملك قوة الفكر."),
        Pair("شوقي ضيف", "أحيانًا، يكون الصمت هو أبلغ أنواع التواصل.")
    )



    private lateinit var timer: CountDownTimer
    private val timerDuration: Long = 60 * 60 * 24 * 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        displayRandomQuote()
        startTimer()

        // Button to share the current quote
        binding.imageButton4.setOnClickListener {
            shareVia(binding.textView3)
        }

        // Button to save the current quote to favorites
        binding.imageButton3.setOnClickListener {
            val intent = Intent(this, fav::class.java)
            intent.putExtra("quote", binding.textView3.text.toString())
            startActivity(intent)
        }

        // Button to navigate to favorites without passing a quote
        binding.imageButton.setOnClickListener {
            val intent = Intent(this, fav::class.java)
            startActivity(intent)
        }

        binding.imageButton2.setOnClickListener {
            openAlertDialog()
        }


        var noti = intent.getStringExtra("quote")


        if (noti != null && noti.isNotEmpty()){

            binding.textView3.text = noti

        }


        displaySavedImageAndColor()
    }

    private fun openAlertDialog() {
        val listOfImages = arrayListOf(
            R.drawable.ba1, R.drawable.ba2, R.drawable.ba3, R.drawable.ba4,
            R.drawable.ba5, R.drawable.ba6, R.drawable.ba7, R.drawable.ba8,
            R.drawable.ba9, R.drawable.ba10, R.drawable.ba11, R.drawable.ba12, R.drawable.ba13
        )

        val listOfColors = arrayListOf(
            "#015A5E", "#9C561B", "#C51905", "#FFFFFF",
            "#A22415", "#7ABEED", "#F17249", "#FFE9C7",
            "#2E1F56", "#FAF4E6", "#43221D", "#462948", "#14263C"
        )

        // Create a LinearLayout for adding the images and colors dynamically
        val dialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null)
        val layout: LinearLayout = dialogView.findViewById(R.id.layout) // The parent layout

        // Loop through each image and color
        for (i in listOfImages.indices) {
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 250
            )
            imageView.setImageResource(listOfImages[i])
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            imageView.setOnClickListener {
                val selectedImage = listOfImages[i]
                val selectedColor = listOfColors[i]

                binding.main.setBackgroundColor(Color.parseColor(selectedColor))
                binding.imageView3.setImageResource(selectedImage)

                saveImageAndColor(selectedImage, selectedColor)
            }
            layout.addView(imageView)
        }

        // Build the AlertDialog
        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }

    private fun saveImageAndColor(imageResId: Int, colorCode: String) {
        // Use SharedPreferences to save the selected image and color
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putInt("selectedImage", imageResId)
        editor.putString("selectedColor", colorCode)

        editor.apply()

        // Display the saved image and color after selection
        displaySavedImageAndColor()
    }

    private fun displaySavedImageAndColor() {
        // Retrieve the saved image and color
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val savedImage = sharedPreferences.getInt("selectedImage", R.drawable.ba10)  // Default image if none is saved
        val savedColor = sharedPreferences.getString("selectedColor", "#FAF4E6")  // Default color if none is saved

        // Apply the saved image and color
        binding.main.setBackgroundColor(Color.parseColor(savedColor))
        binding.imageView3.setImageResource(savedImage)
    }

    private fun shareVia(view: View) {
        try {
            val bitmap = getBitmapFromView(view)
            val uri = saveBitmapToFile(bitmap)

            if (uri != null) {
                shareImage(uri)
            } else {
                Toast.makeText(this, "Failed to share content", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
            print(e)
        }
    }

    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun saveBitmapToFile(bitmap: Bitmap): Uri? {
        val fileName = "shared_text.png"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(storageDir, fileName)

        return try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()

            // Use FileProvider to get the content URI
            FileProvider.getUriForFile(this, "${applicationContext.packageName}.provider", file)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun shareImage(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun startTimer() {
        timer = object : CountDownTimer(timerDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hours = millisUntilFinished / (1000 * 60 * 60)
                val minutes = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60)
                val seconds = (millisUntilFinished % (1000 * 60)) / 1000
                binding.textView2.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            }

            override fun onFinish() {
                binding.textView2.text = "Time's up!"
                displayRandomQuote()
                startTimer() // Restart the timer
            }
        }

        timer.start()
    }

    private fun displayRandomQuote() {
        val randomQuote = quotes[Random.nextInt(quotes.size)]
        val formattedQuote = "\"${randomQuote.first}\"\n- ${randomQuote.second}"
        binding.textView3.text = formattedQuote
        sendNotification(formattedQuote)
    }

    @SuppressLint("NotificationPermission")
    private fun sendNotification(quote: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("quote", quote)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        // Create a PendingIntent that wraps the above intent
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create the notification
        val notification = NotificationCompat.Builder(this, "quote_channel")
            .setContentTitle("Random Quote")
            .setContentText(quote)
            .setSmallIcon(R.drawable.side) // Add an appropriate icon
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // Set the PendingIntent to be triggered when tapped
            .build()

        // Get the NotificationManager system service
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel (for Android Oreo and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "quote_channel",
                "Quotes",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Notify with the notification
        notificationManager.notify(0, notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (::timer.isInitialized) {
            timer.cancel() // Cancel the timer to prevent memory leaks
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                displayRandomQuote()
            } else {
                // Permission denied
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PERMISSION = 1
    }
}
