package com.example.antipodpiska.subList

import com.example.antipodpiska.R
/*          "Allboxes.ru", "Amazon Music", "Apple Arcade", "Apple One", "Arzamas",
            "Badoo Premium", "Blinkist", "Bookmate","Boosty.to", "Clubhouse",
            "Discord Nitro", "Dota2 Plus", "Dropbox",
            "YouTube Premium",  "Spotify Premium", "1C", "24ТВ")*/

object ListExistSubs {
    val subNames = arrayOf("Adobe Creative Cloud", "Allboxes.ru",  "Amazon Music", "Amazon Video",  "Apple Arcade",
            "Apple One", "Badoo Premium", "Blinkist", "Bookmate",
            "Boom", "Boosty.to", "Clubhouse", "Coursera", "Deezer",
            "Discord Nitro", "Dota2 Plus", "Dropbox", "Duolingo", "EA play",
            "Ebay", "Eurosport", "Figma", "Fortnite", "GeForce Now",
            "Gett", "Google Drive", "Humble Choice", "IVI", "Instaread",
            "Jira", "Just Dance", "Last Pass", "Lingualeo", "Megogo", "Medium",
            "Microsoft OneDrive", "Mybook", "Netflix", "Okko", "Okko Sport",
            "Ozon premium", "PlayStation Plus", "Pornhub", "START", "SkyEng",
            "SoundCloud", "Spotify Premium", "Storytel", "Tinder", "Twitch prime", "Uber",
            "Ubisoft", "VK Combo", "VPN99", "VSCO","Voka.tv",
            "Wattpad", "Wink", "Xbox Game Pass", "YouTube Music", "YouTube Premium",
            "more.tv", "Nintendo Switch Online", "Origin Access Premier", "Rebillia.com", "Recurly.com",
            "Trello", "Tvzavr", "Wakanim", "xvideos", "Zoom",
            "Амедиатека", "Ведомости", "Кинопоиск HD", "ЛитРес", "МТС Music",
            "Почта пресс онлайн", "СберЗвук", "СберПрайм", "Тинькофф", "ТНТ premier",
            "Халва.Десятка", "Яндекс.Диск", "Яндекс.Драйв", "Яндекс.Плюс", "Яндекс.Такси",
            "Яндекс.Музыка", "1C", "24ТВ")
/*
Медиум

     )*/

    val subImages = arrayOf("logo_adobe", "logo_allboxes_ru", "logo_amazon_music", "logo_amazon_music", "logo_apple_arcade",
            "logo_apple_one", "logo_badoo", "logo_blinkist", "logo_bookmate",
            "logo_boom", "logo_boosty", "logo_clubhouse", "logo_coursera", "logo_deezer",
            "logo_discord", "logo_dota", "logo_dropbox", "logo_duolign", "logo_ea",
            "logo_ebay", "logo_euro", "logo_figma", "logo_fortnite", "logo_geforce",
            "logo_gett", "logo_gdrive", "logo_humble", "logo_ivi", "logo_instaread",
            "logo_jira", "logo_dance", "logo_last_pass", "logo_lengualeo", "logo_megogo", "logo_medium",
            "logo_onedrive", "logo_mybook", "logo_netflix", "logo_okko", "logo_okko",
            "logo_ozon", "logo_playstation", "logo_pornhub", "logo_start", "logo_skyeng",
            "logo_soundcloud", "logo_spotify", "logo_storytel", "logo_tinder", "logo_twitchprime", "logo_uber",
            "logo_ubisoft", "logo_vk", "logo_vpn99", "logo_vsco", "logo_voka",
            "logo_wattpad", "logo_wink", "logo_xbox", "logo_youtube_premium", "logo_youtube_premium",
            "logo_more", "logo_nintendo", "logo_origin", "logo_rebilla", "logo_recurly",
            "logo_trello", "logo_tvzavr", "logo_wakamin", "logo_xvid", "logo_zoom",
            "logo_amedia", "logo_vedom", "logo_kinopoisk", "logo_litres", "logo_mts",
            "logo_pochta",  "logo_sber_zvuk",  "logo_sber",  "logo_tinkoff",  "logo_tnt",
            "logo_halva", "logo_yadisk", "logo_yadrive", "logo_yaplus", "logo_yataxi",
            "logo_yamusic", "logo_1c", "logo_24tv")

    val subDescriptions = arrayOf("https://www.adobe.com/", "Allboxes.ru", "https://music.amazon.com/", "https://www.primevideo.com/","https://www.apple.com/apple-arcade/",
            "https://www.apple.com/ru/apple-one/", "https://badoo.com/", "https://play.google.com/store/apps/details?id=com.blinkslabs.blinkist.android&hl=ru&gl=US", "https://ru.bookmate.com/subscription",
            "https://boom.ru", "https://boosty.to/", "https://play.google.com/store/apps/details?id=io.clubhouse.clubhouse&hl=ru&gl=US", "https://www.coursera.org/", "https://www.deezer.com/ru/",
           "https://support.discord.com/hc/ru", "http://www.dota2.com/plus?l=russian", "https://www.dropbox.com/h","https://ru.duolingo.com/", "https://www.ea.com",
            "ru.ebay.com", "https://www.eurosport.ru/", "https://www.figma.com/", "https://www.epicgames.com/fortnite/ru/fortnite-crew-subscription", "https://www.nvidia.com/ru-ru/geforce-now/",
            "https://gett.com/ru/", "https://drive.google.com/", "https://ru.humblebundle.com/subscription", "https://www.ivi.ru/", "https://instaread.co/",
            "https://www.atlassian.com/software/jira", "https://justdancenow.com","https://www.lastpass.com", "https://lingualeo.com/ru", "https://megogo.ru/ru", "https://medium.com/",
            "https://onedrive.live.com/", "https://mybook.ru/", "https://www.netflix.com/ru/", "https://okko.tv/", "https://okko.sport/sport",
            "https://www.ozon.ru/", "https://store.playstation.com/","https://www.pornhub.com", "https://start.ru/", "https://skyeng.ru/",
            "https://soundcloud.com/", "https://www.spotify.com/ru-ru/premium/", "https://www.storytel.com/ru/ru/subscriptions", "https://tinder.com/ru", "https://www.twitch.tv/", "https://www.uber.com/",
            "https://www.ubisoft.com/ru-ru/", "https://vkcombo.ru/", "https://vpn99.net/ru", "https://vsco.co/", "https://voka.tv/",
            "https://www.wattpad.com/", "https://wink.rt.ru/", "https://www.xbox.com/", "https://music.youtube.com/", "https://www.youtube.com/premium",
            "https://more.tv/",  "https://www.nintendo.ru/", "https://www.origin.com/rus/ru-ru/store/ea-play", "https://www.rebillia.com/", "https://recurly.com/",
            "https://trello.com/", "https://www.tvzavr.ru/", "https://www.wakanim.tv/", "https://www.xvideos.com/", "https://zoom.us/",
            "https://www.amediateka.ru/", "https://www.vedomosti.ru/", "https://hd.kinopoisk.ru/", "ЛитРес", "https://music.mts.ru/",
            "https://www.pochta.ru/support/subscription/online", "https://sber-zvuk.com/", "https://sberprime.sber.ru/", "https://www.tinkoff.ru/pro/", "https://premier.one/",
            "https://halvacard.ru/halvadesyatka/", "https://disk.yandex.ru", "https://yandex.ru/drive", "https://plus.yandex.ru/", "https://taxi.yandex.ru/",
            "https://music.yandex.ru/", "https://www.1c-interes.ru/interesplus/", "https://24h.tv/"
         )

    val subTypes = arrayOf("Приложения", "Иное", "Музыка",  "Приложения", "Приложения",
            "Приложения", "Приложения",  "Приложения", "Иное",
            "Приложения", "Иное", "Приложения",  "Приложения", "Музыка",
            "Связь", "Приложения",  "Облака", "Приложения", "Приложения",
            "Иное", "Иное", "Приложения", "Приложения", "Иное", "Иное",
            "Приложения", "Приложения", "Приложения", "Иное",  "Иное",
            "Иное",  "Приложения", "Приложения", "Приложения", "Приложения", "Иное",
            "Облака",  "Приложения",  "Смарт-Тв",  "Смарт-Тв", "Смарт-Тв",
            "Иное", "Приложения", "Иное", "Смарт-Тв", "Иное",
            "Приложения", "Приложения", "Иное", "Приложения", "Приложения", "Приложения",
            "Приложения", "Приложения", "Приложения","Иное","Смарт-Тв",
            "Приложения", "Смарт-Тв", "Иное", "Музыка", "Приложения",
            "Смарт-Тв", "Приложения", "Приложения", "Иное", "Иное",
            "Приложения", "Смарт-Тв", "Иное", "Иное", "Связь",
            "Иное", "Иное", "Смарт-Тв", "Иное", "Музыка",
            "Иное", "Музыка", "Иное", "Иное", "Иное",
            "Иное", "Приложения", "Приложения", "Приложения", "Приложения",
            "Музыка", "Иное", "Смарт-Тв"
    )

    val subColors = arrayOf("adobe", "white", "black", "black", "apple_arcade",
            "white", "black", "white", "bookmate",
            "boom", "white", "clubhouse", "coursera", "deezer",
            "discord", "black", "dropbox", "duolingo", "ea",
            "white", "black", "white", "black", "geforce",
            "gett", "white", "hum", "ivi", "intarted",
            "white", "white", "lastpass", "lengualeo", "black", "black",
            "onedrive", "mybook", "black", "okko", "okko",
            "ozon", "playstation", "black", "start", "skyeng",
            "soundcloud", "spotify", "storytel", "tinder", "twitchprime", "black",
            "black", "vk", "white", "black", "voka",
            "wattpad", "wink",  "xbox",  "black", "youtube_back",
            "more", "nintendo", "origin", "white", "recurly",
            "trello", "white", "black", "black", "zoom",
            "black", "vedom", "kinopoisk", "litres", "mts",
            "pochta", "white", "white", "tink", "black",
            "halva", "white", "white", "white", "white",
            "white", "white", "white")
}

