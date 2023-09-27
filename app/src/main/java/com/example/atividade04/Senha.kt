package com.example.atividade04

import java.io.Serializable

class Senha : Serializable {

    private var descricao: String

    private var senha: String

    private var temMaiusculo: Boolean

    private var temNumerico: Boolean

    private var temSimbolo: Boolean

    constructor(senha: String, descricao: String, temMaiusculo: Boolean, temNumerico: Boolean, temSimbolo: Boolean) {
        this.senha = senha
        this.descricao = descricao
        this.temMaiusculo = temMaiusculo
        this.temNumerico = temNumerico
        this.temSimbolo = temSimbolo
    }

    fun getDescricao(): String {
        return this.descricao
    }

    fun setDescricao(descricao: String) {
        this.descricao = descricao
    }

    fun getSenha(): String {
        return this.senha
    }

    fun setSenha(senha: String) {
        this.senha = senha
    }

    fun getTemMaiusculo(): Boolean {
        return this.temMaiusculo
    }

    fun setTemMaiusculo(temMaiusculo: Boolean) {
        this.temMaiusculo = temMaiusculo
    }

    fun getTemNumerico(): Boolean {
        return this.temNumerico
    }

    fun setTemNumerico(temNumerico: Boolean) {
        this.temNumerico = temNumerico
    }

    fun getTemSimbolo(): Boolean {
        return this.temSimbolo
    }

    fun setTemSimbolo(temSimbolo: Boolean) {
        this.temSimbolo = temSimbolo
    }

    override fun toString(): String {
        return "${this.descricao} (${this.senha.length})"
    }
}