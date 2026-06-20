package com.example.data

data class Verse(
    val number: Int,
    val arabic: String,
    val bangla: String,
    val english: String
)

data class Surah(
    val id: Int,
    val englishName: String,
    val arabicName: String,
    val banglaName: String,
    val type: String, // "Meccan" or "Medinan"
    val typeBn: String, // "মাক্কী"or "মাদানী"
    val verseCount: Int,
    val verses: List<Verse>
)

data class Hadith(
    val id: Int,
    val topicEn: String,
    val topicBn: String,
    val narratorEn: String,
    val narratorBn: String,
    val banglaText: String,
    val englishText: String,
    val source: String
)

data class Dua(
    val id: Int,
    val categoryEn: String,
    val categoryBn: String,
    val titleEn: String,
    val titleBn: String,
    val arabic: String,
    val pronunciationBn: String,
    val pronunciationEn: String,
    val meaningBn: String,
    val meaningEn: String
)

data class District(
    val nameEn: String,
    val nameBn: String,
    val lat: Double,
    val lon: Double,
    val timezoneOffset: Double = 6.0
)

object IslamicData {
    val districts = listOf(
        District("Dhaka", "ঢাকা", 23.8103, 90.4125),
        District("Chittagong", "চট্টগ্রাম", 22.3569, 91.7832),
        District("Sylhet", "সিলেট", 24.8949, 91.8687),
        District("Rajshahi", "রাজশাহী", 24.3636, 88.6241),
        District("Khulna", "খুলনা", 22.8456, 89.5403),
        District("Barisal", "বরিশাল", 22.7010, 90.3535),
        District("Rangpur", "রংপুর", 25.7558, 89.2444),
        District("Mymensingh", "ময়মনসিংহ", 24.7471, 90.4203),
        District("Cox's Bazar", "কক্সবাজার", 21.4272, 92.0058),
        District("Comilla", "কুমিল্লা", 23.4607, 91.1809),
        District("Noakhali", "নোয়াখালী", 22.8696, 91.0969),
        District("Bogra", "বগুড়া", 24.8481, 89.3730),
        District("Jessore", "যশোর", 23.1664, 89.2081),
        District("Dinajpur", "দিনাজপুর", 25.6217, 88.6358),
        District("Kushtia", "কুষ্টিয়া", 23.9028, 89.1194),
        District("Tangail", "টাঙ্গাইল", 24.2513, 89.9167),
        District("Faridpur", "ফরিদপুর", 23.6071, 89.8429),
        District("Pabna", "পাবনা", 24.0150, 89.2447),
        District("Feni", "ফেনী", 23.0135, 91.3999),
        District("Jamalpur", "জামালপুর", 24.9192, 89.9482)
    )

    val popularSurahs = listOf(
        Surah(
            id = 1,
            englishName = "Al-Fatihah",
            arabicName = "الفاتحة",
            banglaName = "আল-ফাতিহা",
            type = "Meccan",
            typeBn = "মাক্কী",
            verseCount = 7,
            verses = listOf(
                Verse(1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "পরম করুণাময়, অতি দয়ালু আল্লাহর নামে (শুরু করছি)।", "In the name of Allah, the Entirely Merciful, the Especially Merciful."),
                Verse(2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "সব প্রশংসা জগৎসমূহের প্রতিপালক আল্লাহরই জন্য।", "[All] praise is [due] to Allah, Lord of the worlds -"),
                Verse(3, "الرَّحْمَٰنِ الرَّحِيمِ", "যিনি পরম করুণাময়, অতি দয়ালু।", "The Entirely Merciful, the Especially Merciful,"),
                Verse(4, "مَالِكِ يَوْمِ الدِّينِ", "যিনি বিচার দিনের মালিক।", "Sovereign of the Day of Recompense."),
                Verse(5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "আমরা কেবল আপনারই ইবাদত করি এবং কেবল আপনারই সাহায্য চাই।", "It is You we worship and You we ask for help."),
                Verse(6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "আমাদের সরল পথ দেখান।", "Guide us to the straight path -"),
                Verse(7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "তাদের পথ, যাদের আপনি নিয়ামত দান করেছেন; তাদের পথ নয় যারা ক্রোধ নিপতিত হয়েছে এবং যারা পথভ্রষ্ট হয়েছে।", "The path of those upon whom You have bestowed favor, not of those who have earned [Your] anger or of those who are astray.")
            )
        ),
        Surah(
            id = 36,
            englishName = "Ya-Sin",
            arabicName = "يس",
            banglaName = "ইয়াসীন",
            type = "Meccan",
            typeBn = "মাক্কী",
            verseCount = 12, // Shortened to key verses for beautiful performance
            verses = listOf(
                Verse(1, "يس", "ইয়া-সীন।", "Ya, Seen."),
                Verse(2, "وَالْقُرْآنِ الْحَكِيمِ", "বিজ্ঞানময় কুরআনের শপথ,", "By the wise Qur'an,"),
                Verse(3, "إِنَّكَ لَمِنَ الْمُرْسَلِينَ", "নিশ্চয়ই আপনি রাসূলদের অন্তর্ভুক্ত,", "Indeed you, [O Muhammad], are of the messengers,"),
                Verse(4, "عَلَىٰ صِرَاطٍ مُسْتَقِيمٍ", "সরল পথের উপর প্রতিষ্ঠিত।", "On a straight path."),
                Verse(5, "تَنزِيلَ الْعَزِيزِ الرَّحِيمِ", "এটি মহা পরাক্রমশালী, পরম দয়ালু আল্লাহর অবতীর্ণ।", "[This is] a revelation of the Exalted in Might, the Merciful,"),
                Verse(6, "لِتُنذِرَ قَوْمًا مَّا أُنذِرَ آبَاؤُهُمْ فَهُمْ غَافِلُونَ", "যাতে আপনি এমন এক জাতিকে সতর্ক করতে পারেন যাদের বাপদাদাদের সতর্ক করা হয়নি, ফলে তারা গাফেল হয়ে রয়েছে।", "That you may warn a people whose forefathers were not warned, so they are heedless."),
                Verse(7, "لَقَدْ حَقَّ الْقَوْلُ عَلَىٰ أَكْثَرِهِمْ فَهُمْ لَا يُؤْمِنُونَ", "নিশ্চয়ই তাদের অধিকাংশের উপর কথা অবধারিত হয়ে গেছে, ফলে তারা ঈমান আনবে না।", "Already the word has come into effect upon most of them, so they do not believe."),
                Verse(8, "إِنَّا جَعَلْنَا فِي أَعْنَاقِهِمْ أَغْلَالًا فَهِيَ إِلَى الْأَذْقَانِ فَهُم مُّقْمَحُونَ", "নিশ্চয়ই আমি তাদের গলায় বেড়ি পরিয়ে দিয়েছি, যা চিবুক পর্যন্ত আটকে রয়েছে। ফলে তারা মাথা উঁচু করে আছে।", "Indeed, We have put shackles on their necks, and they are to their chins, so they are with heads kept aloft."),
                Verse(9, "وَجَعَلْنَا مِن بَيْنِ أَيْدِيهِمْ سَدًّا وَمِنْ خَلْفِهِمْ سَدًّا فَأَغْشَيْنَاهُمْ فَهُمْ لَا يُبْصِرُونَ", "এবং আমি তাদের সামনে প্রাচীর ও পেছনে প্রাচীর স্থাপন করেছি, অতঃপর তাদের ঢেকে দিয়েছি, ফলে তারা দেখতে পায় না।", "And We have put before them a barrier and behind them a barrier and covered them, so they do not see."),
                Verse(10, "وَسَوَاءٌ عَلَيْهِمْ أَأَنذَرْتَهُمْ أَمْ لَمْ تُنذِرْهمْ لَا يُؤْمِنُونَ", "আপনি তাদের সতর্ক করুন বা না করুন, উভয়ই তাদের জন্য সমান; তারা ঈমান আনবে না।", "And it is all the same for them whether you warn them or have not warned them - they will not believe."),
                Verse(11, "إِنَّمَا تُنذِرُ مَنِ اتَّبَعَ الذِّكْرَ وَخَشِيَ الرَّحْمَٰنَ بِالْغَيْبِ ۖ فَبَشِّرْهُ بِمَغْفِرَةٍ وَأَجْرٍ كَرِيمٍ", "আপনি কেবল তাকেই সতর্ক করতে পারেন যে উপদেশ মেনে চলে এবং পরম করুণাময়কে না দেখে ভয় করে। অতএব তাকে ক্ষমা ও সম্মানজনক প্রতিদানের সুসংবাদ দিন।", "You can only warn one who follows the message and fears the Entirely Merciful unseen. So give him good tidings of forgiveness and noble reward."),
                Verse(12, "إِنَّا نَحْنُ نُحْيِي الْمَوْتَىٰ وَنَكْتُبُ مَا قَدَّمُوا وَآثَارَهُمْ ۚ وَكُلَّ شَيْءٍ أَحْصَيْنَاهُ فِي إِمَامٍ مُّبِينٍ", "নিশ্চয়ই আমি মৃতদের জীবিত করি এবং তারা যা পাঠিয়েছে তা ও তাদের রেখে যাওয়া চিহ্নসমূহ লিখে রাখি। আর আমি প্রতিটি জিনিস স্পষ্ট কিতাবে সংরক্ষণ করেছি।", "Indeed, it is We who bring the dead to life and record what they have put forth and what they left behind, and all things We have enumerated in a clear register.")
            )
        ),
        Surah(
            id = 55,
            englishName = "Ar-Rahman",
            arabicName = "الرحمن",
            banglaName = "আর-রহমান",
            type = "Medinan",
            typeBn = "মাদানী",
            verseCount = 10,
            verses = listOf(
                Verse(1, "الرَّحْمَٰنُ", "পরম করুণাময়।", "The Entirely Merciful"),
                Verse(2, "عَلَّمَ الْقُرْآنَ", "তিনি শিক্ষা দিয়েছেন কুরআন।", "Taught the Qur'an,"),
                Verse(3, "خَلَقَ الْإِنسَانَ", "সৃষ্টি করেছেন মানুষ।", "Created man,"),
                Verse(4, "عَلَّمَهُ الْبَيَانَ", "তাকে শিখিয়েছেন ভাষা প্রকাশ করতে।", "[And] taught him eloquence."),
                Verse(5, "الشَّمْسُ وَالْقَمَرُ بِحُسْبَانٍ", "সূর্য এবং চন্দ্র আবর্তিত হচ্ছে সুনির্দিষ্ট হিসাবে।", "The sun and the moon [move] by precise calculation,"),
                Verse(6, "وَالنَّجْمُ وَالشَّجَرُ يَسْجُدَانِ", "এবং লতা পাতা ও বৃক্ষরাজি সাজদা করছে।", "And the stars and trees prostrate."),
                Verse(7, "وَالسَّمَاءَ رَفَعَهَا وَوَضَعَ الْمِيزَانَ", "এবং তিনি আকাশকে করেছেন সমুন্নত এবং স্থাপন করেছেন দাঁড়িপাল্লা (ভারসাম্য)।", "And the heaven He raised and imposed the balance"),
                Verse(8, "أَلَّا تَطْغَوْا فِي الْمِيزَانِ", "যাতে তোমরা ভারসাম্যে সীমা লঙ্ঘন না কর।", "That you do not transgress within the balance."),
                Verse(9, "وَأَقِيمُوا الْوَزْنَ بِالْقِسْطِ وَلَا تُخْسِرُوا الْمِيزَانَ", "তোমরা সঠিক ওজন কায়েম কর এবং ওজনে কম দিও না।", "And establish weight in justice and do not make deficient the balance."),
                Verse(10, "وَالْأَرْضَ وَضَعَهَا لِلْأَنَامِ", "এবং তিনি পৃথিবীকে বিছিয়েছেন সৃষ্টিকুলের জন্য।", "And the earth He laid [out] for the creatures.")
            )
        ),
        Surah(
            id = 112,
            englishName = "Al-Ikhlas",
            arabicName = "الإخلاص",
            banglaName = "আল-ইখলাস",
            type = "Meccan",
            typeBn = "মাক্কী",
            verseCount = 4,
            verses = listOf(
                Verse(1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "বলুন, তিনিই আল্লাহ, একক-অদ্বিতীয়।", "Say, \"He is Allah, [who is] One,"),
                Verse(2, "اللَّهُ الصَّمَدُ", "আল্লাহ কারো মুখাপেক্ষী নন, সকলেই তাঁর মুখাপেক্ষী।", "Allah, the Eternal Refuge."),
                Verse(3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "তিনি কাউকে জন্ম দেননি এবং তাঁকেও জন্ম দেয়া হয়নি,", "He neither begets nor is born,"),
                Verse(4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "এবং তাঁর সমকক্ষ কেউই নেই।", "Nor is there to Him any equivalent.\"")
            )
        ),
        Surah(
            id = 113,
            englishName = "Al-Falaq",
            arabicName = "الفلق",
            banglaName = "আল-ফালাক",
            type = "Meccan",
            typeBn = "মাক্কী",
            verseCount = 5,
            verses = listOf(
                Verse(1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "বলুন, আমি আশ্রয় প্রার্থনা করছি ঊষার প্রতিপালকের,", "Say, \"I seek refuge in the Lord of daybreak"),
                Verse(2, "مِن شَرِّ مَا خَلَقَ", "তিনি যা সৃষ্টি করেছেন তার অনিষ্ট হতে,", "From the evil of that which He created"),
                Verse(3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "এবং অন্ধকার রাত্রির অনিষ্ট হতে যখন তা গভীর হয়,", "And from the evil of darkness when it settles"),
                Verse(4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "এবং গ্রন্থিতে ফুঁৎকারদানকারী (যাদুকরদের) অনিষ্ট হতে,", "And from the evil of the blowers in knots"),
                Verse(5, "**وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ**", "এবং হিংসুকের অনিষ্ট হতে যখন সে হিংসা করে।", "And from the evil of an envier when he envies.\"")
            )
        ),
        Surah(
            id = 114,
            englishName = "An-Nas",
            arabicName = "الناس",
            banglaName = "আন-নাস",
            type = "Meccan",
            typeBn = "মাক্কী",
            verseCount = 6,
            verses = listOf(
                Verse(1, "قُل *أَعُوذُ بِرَبِّ النَّاسِ*", "বলুন, আমি আশ্রয় প্রার্থনা করছি মানুষের প্রতিপালকের,", "Say, \"I seek refuge in the Lord of mankind,"),
                Verse(2, "مَلِكِ النَّاسِ", "মানুষের অধিপতির,", "The Sovereign of mankind,"),
                Verse(3, "إِلَٰهِ النَّاسِ", "মানুষের মা’বুদের,", "The God of mankind,"),
                Verse(4, "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ", "পলায়নপর প্ররোচনাকারীর অনিষ্ট হতে,", "From the evil of the retreating whisperer -"),
                Verse(5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "যে মানুষের অন্তরে প্ররোচনা দেয়,", "Who whispers [evil] into the breasts of mankind -"),
                Verse(6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "জ্বিন ও মানুষের মধ্য হতে।", "From among the jinn and mankind.\"")
            )
        )
    )

    val keyHadiths = listOf(
        Hadith(
            id = 1,
            topicEn = "Sincerity (Niyyah)",
            topicBn = "নিয়্যত বা নিয়ত",
            narratorEn = "Umar bin Al-Khattab",
            narratorBn = "হযরত ওমর বিন খাত্তাব (রাঃ)",
            banglaText = "রাসূলুল্লাহ (সাঃ) বলেছেন: 'সমস্ত আমল নিয়তের উপর নির্ভরশীল। আর প্রত্যেক ব্যক্তি তাই পাবে যা সে নিয়ত করবে।' (সহীহ বুখারী: ১)",
            englishText = "Messenger of Allah (ﷺ) said: 'The reward of deeds depends upon the intentions and every person will get the reward according to what he has intended.' (Sahih al-Bukhari: 1)",
            source = "Sahih al-Bukhari"
        ),
        Hadith(
            id = 2,
            topicEn = "Character (Akhlaq)",
            topicBn = "উত্তম চরিত্র",
            narratorEn = "Abu Hurayrah",
            narratorBn = "হযরত আবু হুরায়রা (রাঃ)",
            banglaText = "রাসূলুল্লাহ (সাঃ) বলেছেন: 'নিশ্চয়ই আমি প্রেরিত হয়েছি উত্তম চরিত্রের পূর্ণতা সাধনের জন্য।' (মুসনাদে আহমাদ: ৮৯৩৯)",
            englishText = "The Messenger of Allah (ﷺ) said: 'I was sent only to perfect good character.' (Musnad Ahmad: 8939)",
            source = "Musnad Ahmad & Al-Adab Al-Mufrad"
        ),
        Hadith(
            id = 3,
            topicEn = "Belief (Iman)",
            topicBn = "ঈমান বা বিশ্বাস",
            narratorEn = "Anas bin Malik",
            narratorBn = "হযরত আনাস বিন মালিক (রাঃ)",
            banglaText = "নবী করীম (সাঃ) বলেছেন: 'তোমাদের কেউ প্রকৃত মুমিন হতে পারবে না, যতক্ষণ না সে তার ভাইয়ের জন্য তা পছন্দ করবে যা সে নিজের জন্য পছন্দ করে।' (সহীহ বুখারী: ১৩)",
            englishText = "The Prophet (ﷺ) said: 'None of you will have faith till he wishes for his brother what he likes for himself.' (Sahih al-Bukhari: 13)",
            source = "Sahih al-Bukhari"
        ),
        Hadith(
            id = 4,
            topicEn = "Prayer (Salah)",
            topicBn = "সালাত বা নামায",
            narratorEn = "Jabir bin Abdullah",
            narratorBn = "হযরত জাবির (রাঃ)",
            banglaText = "আমি নবী করীম (সাঃ)-কে বলতে শুনেছি: 'নিশ্চয়ই বান্দা এবং শিরক ও কুফরের মধ্যে পার্থক্য হলো সালাত বর্জন করা।' (সহীহ মুসলিম: ৮২)",
            englishText = "The Prophet (ﷺ) said: 'Between a man and shirk and disbelief is the abandonment of prayer.' (Sahih Muslim: 82)",
            source = "Sahih Muslim"
        ),
        Hadith(
            id = 5,
            topicEn = "Kindness (Rahmah)",
            topicBn = "দয়া ও দাক্ষিণ্য",
            narratorEn = "Abdullah bin Amr",
            narratorBn = "হযরত আবদুল্লাহ ইবনে আমর (রাঃ)",
            banglaText = "রাসূলুল্লাহ (সাঃ) বলেছেন: 'দয়াশীলদের ওপর পরম দয়াময় আল্লাহ দয়া করেন। তোমরা জমিনবাসীদের প্রতি দয়া করো, তাহলে আসমানবাসী (আল্লাহ) তোমাদের প্রতি দয়া করবেন।' (সুনানে আবু দাউদ: ৪৯৪১1)",
            englishText = "The Messenger of Allah (ﷺ) said: 'The Merciful One shows mercy to those who are merciful. Be merciful to those on the earth, and He who is in heaven will show mercy to you.' (Sunan Abi Dawud: 4941)",
            source = "Sunan Abi Dawud"
        )
    )

    val importantDuas = listOf(
        Dua(
            id = 1,
            categoryEn = "Morning & Evening",
            categoryBn = "সকাল ও সন্ধ্যা",
            titleEn = "Protection from Harm",
            titleBn = "সকল ক্ষতি থেকে বাঁচার দু’আ",
            arabic = "بِسْمِ اللَّهِ الَّذِي لَا يَضُرُّ مَعَ اسْمِهِ شَيْءٌ فِي الْأَرْضِ وَلَا فِي السَّمَاءِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            pronunciationBn = "বিসমিল্লাহিল্লাযী লা ইয়াদুররু মা’আসমিহী শাইউন ফিল আরদ্বি ওয়ালা ফিসসামা-ই, ওয়া হুয়াস সামীউল আলীম।",
            pronunciationEn = "Bismillahilladhi la yadurru ma'asmihi shay'un fil-ardi wa la fis-sama'i wa Huwas-Sami'ul-'Alim.",
            meaningBn = "আল্লাহর নামে, যাঁর নামের বরকতে আসমান ও জমিনের কোনো কিছুই কোনো ক্ষতি করতে পারে না। তিনি সর্বশ্রোতা, সর্বজ্ঞ। (উচ্চারণ ৩ বার সকাল-সন্ধ্যায়)",
            meaningEn = "In the name of Allah, with Whose name nothing can cause harm in the earth or in the heaven, and He is the All-Hearing, the All-Knowing. (Recite 3 times morning & evening)"
        ),
        Dua(
            id = 2,
            categoryEn = "Daily Life",
            categoryBn = "দৈনন্দিন জীবন",
            titleEn = "Before Eating",
            titleBn = "খাওয়ার পূর্বের দু’আ",
            arabic = "بِسْمِ اللَّهِ وَعَلَىٰ بَرَكَةِ اللَّهِ",
            pronunciationBn = "বিসমিল্লাহি ওয়া ’আলা বারাকাতিল্লাহ।",
            pronunciationEn = "Bismillahi wa 'ala barakatillah.",
            meaningBn = "আল্লাহর নামে এবং আল্লাহর বরকতের উপর ভরসা করে খাওয়া শুরু করছি।",
            meaningEn = "In the name of Allah and with the blessings of Allah."
        ),
        Dua(
            id = 3,
            categoryEn = "Daily Life",
            categoryBn = "দৈনন্দিন জীবন",
            titleEn = "After Eating",
            titleBn = "খাবার শেষের দু’আ",
            arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنَا وَسَقَانَا وَجَعَلَنَا مُسْلِمِينَ",
            pronunciationBn = "আলহামদু লিল্লাহিল্লাযী আত’আমানা ওয়া সাক্বানা ওয়া জা’আলানা মুসলিমীন।",
            pronunciationEn = "Alhamdu lillahilladhi at'amana wa saqana wa ja'alana Muslimin.",
            meaningBn = "সমস্ত প্রশংসা আল্লাহর জন্য, যিনি আমাদের আহার করিয়েছেন, পান করিয়েছেন এবং মুসলমান বানিয়েছেন।",
            meaningEn = "Praise be to Allah Who has fed us and given us drink, and made us Muslims."
        ),
        Dua(
            id = 4,
            categoryEn = "Home & Travel",
            categoryBn = "ঘর ও সফর",
            titleEn = "Before Sleeping",
            titleBn = "ঘুমানোর পূর্বের দু’আ",
            arabic = "بِاسْمِكَ اللَّهُمَّ أَمُوتُ وَأَحْيَا",
            pronunciationBn = "বিইসমিকা আল্লাহুম্মা আমূতু ওয়া আহ্ইয়া।",
            pronunciationEn = "Bismika Allahumma amutu wa ahya.",
            meaningBn = "হে আল্লাহ! আপনারই নামে আমি মৃত্যুবরণ (ঘুমাচ্ছি) করছি এবং জীবিত হচ্ছি (জাগছি)।",
            meaningEn = "In Your name, O Allah, I die and I live."
        ),
        Dua(
            id = 5,
            categoryEn = "Home & Travel",
            categoryBn = "ঘর ও সফর",
            titleEn = "Leaving Home",
            titleBn = "ঘর থেকে বের হওয়ার দু’আ",
            arabic = "بِسْمِ اللَّهِ تَوَكَّلْتُ عَلَى اللَّهِ وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
            pronunciationBn = "বিসমিল্লাহি তাওয়াক্কালতু ’আলাল্লাহি, ওয়া লা হাওলা ওয়া লা কুওয়াতা ইল্লা বিল্লাহ।",
            pronunciationEn = "Bismillahi tawakkaltu 'alallah, wa la hawla wa la quwwata illa billah.",
            meaningBn = "আল্লাহর নামে, আল্লাহর ওপর ভরসা করলাম। আল্লাহর সাহায্য ছাড়া গুনাহ থেকে বাঁচার এবং নেক কাজ করার কোনো শক্তি নেই।",
            meaningEn = "In the name of Allah, I place my trust in Allah, and there is no might nor power except with Allah."
        ),
        Dua(
            id = 6,
            categoryEn = "Mosque",
            categoryBn = "মসজিদ",
            titleEn = "Entering the Mosque",
            titleBn = "মসজিদে প্রবেশের দু’আ",
            arabic = "اللَّهُمَّ افْتَحْ لِي أَبْوَابَ رَحْمَتِكَ",
            pronunciationBn = "আল্লাহুম্মাফ তাহলীল আবওয়াবা রাহমাতিক।",
            pronunciationEn = "Allahummaf-tah li abwaba rahmatik.",
            meaningBn = "হে আল্লাহ! আমার জন্য আপনার রহমতের দরজাগুলো খুলে দিন।",
            meaningEn = "O Allah, open for me the gates of Your mercy."
        )
    )
}
