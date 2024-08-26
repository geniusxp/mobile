package com.github.ericknathan.geniusxp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ericknathan.geniusxp.R
import com.github.ericknathan.geniusxp.models.Lecture
import com.github.ericknathan.geniusxp.models.Speaker
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.LectureListAdapter
import com.github.ericknathan.geniusxp.ui.recyclerview.adapter.SpeakerListAdapter

class SpeakersFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_speakers, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.speakerList)
        recyclerView.adapter = SpeakerListAdapter(view.context, listOf(
            Speaker("ThePrimeagen", "Streamer e Youtuber americano conhecido por sua obsessão por Neovim, engenharia e atualmente trabalha na Netflix.", "https://yt3.googleusercontent.com/ytc/AIdro_laY82JAzs_2edBDxrxLgLWshhMK04SpAqOfoEzexOBZg=s900-c-k-c0x00ffffff-no-rj", "https://www.linkedin.com/in/theprimeagen-multi-billion-9699184a", "\uD83C\uDDFA\uD83C\uDDF8"),
            Speaker("Rafaella Ballerini", "Criadora de conteúdo de tecnologia, guiando pessoas que desejam iniciar na área de desenvolvimento de software.", "https://avatars.githubusercontent.com/u/54322854?v=4", "https://www.linkedin.com/in/rafaellaballerini", "\uD83C\uDDE7\uD83C\uDDF7"),
            Speaker("Keit Oliveira", "Organizadora responsável pela produção do FRONTIN Sampa, tradicional evento de desenvolvimento web desde 2012.", "https://avatars.githubusercontent.com/u/3808430?v=4", "https://www.linkedin.com/in/keitoliveira", "\uD83C\uDDE7\uD83C\uDDF7"),
            Speaker("Lucas Santos", "Engenheiro de software que vem criando bugs desde 2011.", "https://static-media.hotmart.com/WxCwNlqKeO1BGsKnIlj0pW5YT3E=/filters:quality(1):format(webp)/klickart-prod/uploads/media/file/6567590/avatar_palestra.png", "https://www.linkedin.com/in/lsantosdev", "\uD83C\uDDE7\uD83C\uDDF7"),
        ))
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        return view
    }

}