package com.example.antipodpiska.subList

import com.example.antipodpiska.R
/*          "Allboxes.ru", "Amazon Music", "Apple Arcade", "Apple One", "Arzamas",
            "Badoo Premium", "Blinkist", "Bookmate","Boosty.to", "Clubhouse",
            "Discord Nitro", "Dota2 Plus", "Dropbox",
            "YouTube Premium",  "Spotify Premium", "1C", "24ТВ")*/

object ListExistSubs {
    val subNames = arrayOf("Adobe Creative Cloud", "Allboxes.ru",  "Amazon Music", "Amazon Prime Video",  "Apple Arcade",
            "Apple One", "Arzamas", "Badoo Premium", "Blinkist", "Bookmate",
            "Boom", "Boosty.to", "Clubhouse", "Coursera", "Deezer",
            "Discord Nitro", "Dota2 Plus", "Dropbox", "Duolingo", "EA play",
            "Ebay", "Eurosport", "Figma", "Fortnite", "GeForce Now",
            "Gett", "Google Drive", "Humble Choice", "IVI", "Instaread",
            "Jira", "Just Dance", "Last Pass", "Lingualeo", "Megogo",
            "Microsoft OneDrive", "Mybook", "Netflix", "Okko", "Okko Sport",
            "Ozon premium", "PlayStation Plus", "Pornhub", "START", "SkyEng",
            "SoundCloud", "Storytel", "Tinder", "Twitch prime", "Uber",
            "Ubisoft", "VK Combo", "VPN99", "VSCO","Voka.tv",
            "Wattpad", "Wink", "Xbox Game Pass", "YouTube Music", "YouTube Premium",
            "more.tv", "Nintendo Switch Online", "Origin Access Premier", "Rebillia.com", "Recurly.com",
             "Trello", "Tvzavr", "Wakanim", "xvideos", "Zoom",
            "Амедиатека", "Ведомости", "Кинопоиск HD", "ЛитРес", "МТС Music",

            "Spotify Premium", "1C", "24ТВ")
/*
    "Патефон", "Почта пресс онлайн", "СберЗвук",
    "СберПрайм", "Тинькофф", "ТНТ premier", "Халва.Десятка", "Яндекс.Диск", "Яндекс.Драйв",
    "Яндекс.Плюс", "Яндекс.Такси", "Яндекс.Шеф", "Яндекс.Музыка" )*/

    val subImages = arrayOf(R.drawable.logo_adobe, R.drawable.logo_allboxes_ru, R.drawable.logo_amazon_music, R.drawable.logo_amazon_music, R.drawable.logo_apple_arcade,
            R.drawable.logo_apple_one, R.drawable.logo_arzamas, R.drawable.logo_badoo, R.drawable.logo_blinkist, R.drawable.logo_bookmate,
            R.drawable.logo_boom, R.drawable.logo_boosty, R.drawable.logo_clubhouse, R.drawable.logo_coursera, R.drawable.logo_deezer,
            R.drawable.logo_discord, R.drawable.logo_dota, R.drawable.logo_dropbox, R.drawable.logo_duolign, R.drawable.logo_ea,
            R.drawable.logo_ebay, R.drawable.logo_euro, R.drawable.logo_figma, R.drawable.logo_fortnite, R.drawable.logo_geforce,
            R.drawable.logo_gett, R.drawable.logo_gdrive, R.drawable.logo_humble, R.drawable.logo_ivi, R.drawable.logo_instaread,
            R.drawable.logo_jira, R.drawable.logo_dance, R.drawable.logo_last_pass, R.drawable.logo_lengualeo, R.drawable.logo_megogo,
            R.drawable.logo_onedrive, R.drawable.logo_mybook, R.drawable.logo_netflix, R.drawable.logo_okko, R.drawable.logo_okko,
            R.drawable.logo_ozon, R.drawable.logo_playstation, R.drawable.logo_pornhub, R.drawable.logo_start, R.drawable.logo_skyeng,
            R.drawable.logo_soundcloud, R.drawable.logo_storytel, R.drawable.logo_tinder, R.drawable.logo_twitchprime, R.drawable.logo_uber,
            R.drawable.logo_ubisoft, R.drawable.logo_vk, R.drawable.logo_vpn99, R.drawable.logo_vsco, R.drawable.logo_voka,
            R.drawable.logo_wattpad, R.drawable.logo_wink, R.drawable.logo_xbox, R.drawable.logo_youtube_premium, R.drawable.logo_youtube_premium,
            R.drawable.logo_more, R.drawable.logo_nintendo, R.drawable.logo_origin, R.drawable.logo_rebilla, R.drawable.logo_recurly,
            R.drawable.logo_trello, R.drawable.logo_tvzavr, R.drawable.logo_wakamin, R.drawable.logo_xvid, R.drawable.logo_zoom,
            R.drawable.logo_amedia, R.drawable.logo_vedom, R.drawable.logo_kinopoisk, R.drawable.logo_litres, R.drawable.logo_mts,


            R.drawable.logo_spotify, R.drawable.logo_1c, R.drawable.logo_24tv)

    val subDescriptions = arrayOf("https://www.adobe.com/", "Allboxes.ru", "https://music.amazon.com/", "https://www.primevideo.com/","https://www.apple.com/apple-arcade/",
            "https://www.apple.com/ru/apple-one/", "https://arzamas.academy/account","https://badoo.com/", "https://play.google.com/store/apps/details?id=com.blinkslabs.blinkist.android&hl=ru&gl=US", "https://ru.bookmate.com/subscription",
            "https://boom.ru", "https://boosty.to/", "https://play.google.com/store/apps/details?id=io.clubhouse.clubhouse&hl=ru&gl=US", "https://www.coursera.org/", "https://www.deezer.com/ru/",
           "https://support.discord.com/hc/ru", "http://www.dota2.com/plus?l=russian", "https://www.dropbox.com/h","https://ru.duolingo.com/", "https://www.ea.com",
            "ru.ebay.com", "https://www.eurosport.ru/", "https://www.figma.com/", "https://www.epicgames.com/fortnite/ru/fortnite-crew-subscription", "https://www.nvidia.com/ru-ru/geforce-now/",
            "https://gett.com/ru/", "https://drive.google.com/", "https://ru.humblebundle.com/subscription", "https://www.ivi.ru/", "https://instaread.co/",
            "https://www.atlassian.com/software/jira", "https://justdancenow.com","https://www.lastpass.com", "https://lingualeo.com/ru", "https://megogo.ru/ru",
            "https://onedrive.live.com/", "https://mybook.ru/", "https://www.netflix.com/ru/", "https://okko.tv/", "https://okko.sport/sport",
            "https://www.ozon.ru/", "https://store.playstation.com/","https://www.pornhub.com", "https://start.ru/", "https://skyeng.ru/",
            "https://soundcloud.com/", "https://www.storytel.com/ru/ru/subscriptions", "https://tinder.com/ru", "https://www.twitch.tv/", "https://www.uber.com/",
            "https://www.ubisoft.com/ru-ru/", "https://vkcombo.ru/", "https://vpn99.net/ru", "https://vsco.co/", "https://voka.tv/",
            "https://www.wattpad.com/", "https://wink.rt.ru/", "https://www.xbox.com/", "https://music.youtube.com/", "https://www.youtube.com/premium",
            "https://more.tv/",  "https://www.nintendo.ru/", "https://www.origin.com/rus/ru-ru/store/ea-play", "https://www.rebillia.com/", "https://recurly.com/",
            "https://trello.com/", "https://www.tvzavr.ru/", "https://www.wakanim.tv/", "https://www.xvideos.com/", "https://zoom.us/",
            "https://www.amediateka.ru/", "https://www.vedomosti.ru/", "https://hd.kinopoisk.ru/", "ЛитРес", "https://music.mts.ru/",

            "https://www.spotify.com/ru-ru/premium/","https://www.1c-interes.ru/interesplus/", "https://24h.tv/"
         )
    /*
            "", "", "", "", "",

    */

    val subTypes = arrayOf("Приложения", "Иное", "Музыка",  "Приложения", "Приложения",
            "Приложения", "Приложения", "Приложения",  "Приложения", "Иное",
            "Приложения", "Иное", "Приложения",  "Приложения", "Музыка",
            "Связь", "Приложения",  "Облака", "Приложения", "Приложения",
            "Иное", "Иное", "Приложения", "Приложения", "Иное", "Иное",
            "Приложения", "Приложения", "Приложения", "Иное",  "Иное",
            "Иное",  "Приложения", "Приложения", "Приложения", "Приложения",
            "Облака",  "Приложения",  "Смарт-Тв",  "Смарт-Тв", "Смарт-Тв",
            "Иное", "Приложения", "Иное", "Смарт-Тв", "Иное",
            "Приложения", "Иное", "Приложения", "Приложения", "Приложения",
            "Приложения", "Приложения", "Приложения","Иное","Смарт-Тв",
            "Приложения", "Смарт-Тв", "Иное", "Музыка", "Приложения",
            "Смарт-Тв", "Приложения", "Приложения", "Иное", "Иное",
            "Приложения", "Смарт-Тв", "Иное", "Иное", "Связь",
            "Иное", "Иное", "Смарт-Тв", "Иное", "Музыка",

            "Приложения", "Музыка", "Иное", "Смарт-Тв"
    )

    val subColors = arrayOf(R.color.adobe, R.color.white, R.color.black, R.color.black, R.color.apple_arcade,
            R.color.white, R.color.arzamas, R.color.black, R.color.white, R.color.bookmate,
            R.color.boom, R.color.white, R.color.clubhouse, R.color.coursera, R.color.deezer,
            R.color.discord, R.color.black, R.color.dropbox, R.color.duolingo, R.color.ea,
            R.color.white, R.color.black, R.color.white, R.color.black, R.color.geforce,
            R.color.gett, R.color.white, R.color.hum, R.color.ivi, R.color.intarted,
            R.color.white, R.color.dance, R.color.lastpass, R.color.lengualeo, R.color.black,
            R.color.onedrive, R.color.mybook, R.color.black, R.color.okko, R.color.okko,
            R.color.ozon, R.color.playstation, R.color.black, R.color.start, R.color.skyeng,
            R.color.soundcloud, R.color.storytel, R.color.tinder, R.color.twitchprime, R.color.black,
            R.color.black, R.color.vk, R.color.white, R.color.black, R.color.voka,
            R.color.wattpad, R.color.wink,  R.color.xbox,  R.color.black, R.color.youtube_back,
            R.color.more, R.color.nintendo, R.color.origin, R.color.white, R.color.recurly,
            R.color.trello, R.color.white, R.color.black, R.color.black, R.color.zoom,
            R.color.black, R.color.vedom, R.color.kinopoisk, R.color.litres, R.color.mts,

            R.color.spotify, R.color.white, R.color.white)
}

