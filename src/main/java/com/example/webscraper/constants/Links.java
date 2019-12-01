package com.example.webscraper.constants;

public class Links {
    //Parlamentares
    public static final String PARLAMENTARES_LINK ="https://sapl.recife.pe.leg.br/consultas/parlamentar/parlamentar_index_html";
    //Sessoes Plenarias
    public static final String SESSOES_PLENARIAS_INDEX_LINK ="https://sapl.recife.pe.leg.br/consultas/sessao_plenaria/sessao_plenaria_index_html";
    public static final String SESSAO_PLENARIA_SULFIX="?dat_sessao_sel=";
    public static final String SESSAO_PLENARIA_PAGE_LINK="https://sapl.recife.pe.leg.br/consultas/sessao_plenaria/";
    //Materias Legislativas
    public static final String MATERIAS_LEGISLATIVAS_FIRST_PAGE_LINK = "https://sapl.recife.pe.leg.br/generico/" +
            "materia_pesquisar_proc?page=1&step=8&txt_relator=&txt_numero=&dt_public2=&lst_tip_autor=&txt_num_pro" +
            "tocolo=&hdn_txt_autor=&txt_ano=&hdn_cod_autor=&lst_localizacao=&lst_tip_materia=&txt_assunto=&btn" +
            "_materia_pesquisar=Pesquisar&incluir=0&lst_cod_partido=&dt_apres2=&chk_coautor=&txt_npc=&lst_status" +
            "=&dt_public=&rd_ordenacao=1&rad_tramitando=&existe_ocorrencia=0&dt_apres=";
    //Projetos de Lei Ordinaria
    public static final String PROJETOS_DE_LEI_ORDINARIA_FIRST_PAGE_LINK = "https://sapl.recife.pe.leg.br/generico/materia_pesquisar_proc?page=1&step=8&txt_relator=&txt_numero=&dt_public2=&lst_tip_autor=&txt_num_protocolo=&hdn_txt_autor=&txt_ano=&hdn_cod_autor=&lst_localizacao=&lst_tip_materia=10&txt_assunto=&btn_materia_pesquisar=Pesquisar&incluir=0&lst_cod_partido=&dt_apres2=&chk_coautor=&txt_npc=&lst_status=&dt_public=&rd_ordenacao=1&rad_tramitando=&existe_ocorrencia=0&dt_apres=";
    //Projetos de Resolucao
    public static final String PROJETOS_DE_RESOLUCAO_FIRST_PAGE_LINK = "https://sapl.recife.pe.leg.br/generico/materia_pesquisar_proc?page=1&step=8&txt_relator=&txt_numero=&dt_public2=&lst_tip_autor=&txt_num_protocolo=&hdn_txt_autor=&txt_ano=&hdn_cod_autor=&lst_localizacao=&lst_tip_materia=2&txt_assunto=&btn_materia_pesquisar=Pesquisar&incluir=0&lst_cod_partido=&dt_apres2=&chk_coautor=&txt_npc=&lst_status=&dt_public=&rd_ordenacao=1&rad_tramitando=&existe_ocorrencia=0&dt_apres=";
}
