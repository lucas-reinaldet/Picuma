package faculdade.br.picuma.util;

/**
 * Created by lucas on 18/04/2018.
 */

public class Constantes {

    //URL conexao

    public static final String URL_CONEXAO_WEB_SERVICE = "http://10.0.2.2:8080/PicumaWebService/web";
//    public static final String URL_CONEXAO_WEB_SERVICE = "http://192.168.43.50:8080/PicumaWebService/web";

    //URL BUSCA

    public static final String URL_BUSCA_CEP = "https://viacep.com.br/ws/";
    public static final String FORMATO_BUSCA_JSON = "/json";
    public static final String URL_BUSCA_GEOCODE_ENDERECO = "https://maps.googleapis.com/maps/api/geocode";
    public static final String URL_BUSCA_GEOCODE_ENDERECO_PARANS_ADDRESS = "?address=";
    public static final String URL_BUSCA_GEOCODE_ENDERECO_PARANS_KEY = "&key=AIzaSyDwEotAxxtrzK1tqExi4TiVMgizH6fqe2k";

    //PATH de classe
    public static final String PATH_SERVICOS_OFERTADOS = "/so/";

    //PATH de metodo d
    public static final String PATH_REALIZAR_LOGIN = "rl";
    public static final String PATH_CADASTRO_CLIENTE = "cc";
    public static final String PATH_CADASTRO_EMPRESA = "ce";
    public static final String PATH_LISTAR_AREA_ATUACAO = "laa";
    public static final String PATH_CADASTRO_COMENTARIO = "cco";
    public static final String PATH_CADASTRO_FOTO_GALERIA = "cfg";
    public static final String PATH_CADASTRO_SERVICO_PRESTADO = "csp";
    public static final String PATH_LISTAR_EMPRESAS = "les";
    public static final String PATH_LISTAR_EMPRESA_POR_ID = "lepi";
    public static final String PATH_LISTAR_FOTOS_GALERIA = "lfg";
    public static final String PATH_LISTAR_COMENTARIO = "lco";
    public static final String PATH_LISTAR_SERVICO_PRESTADO = "lsp";
    public static final String PATH_LISTAR_GRUPO_SERVICO = "lgs";
    public static final String PATH_ALTERAR_SERVICO_PRESTADO = "asp";
    public static final String PATH_DELETAR_SERVICO_PRESTADO = "dsp";
    public static final String PATH_ALTERAR_NOME_ATENDIMENTO_EMPRESA = "anae";
    public static final String PATH_ALTERAR_DESCRICAO_EMPRESA = "ade";
    public static final String PATH_ALTERAR_LOGO_EMPRESA = "ale";
    public static final String PATH_ALTERAR_ENDERECO_EMPRESA = "aee";
    public static final String PATH_CADASTRAR_CONTATO_EMPRESA = "cce";
    public static final String PATH_DELETAR_CONTATO_EMPRESA = "dce";
    public static final String PATH_DELETAR_HORARIO_EMPRESA = "dhe";
    public static final String PATH_CADASTRAR_HORARIO_EMPRESA = "che";
    public static final String PATH_CADASTRAR_FUNCIONARIO_EMPRESA = "cfe";
    public static final String PATH_DELETAR_FUNCIONARIO_EMPRESA = "dfe";
    public static final String PATH_DELETAR_FOTO_GALERIA_EMPRESA = "dfge";
    public static final String PATH_ALTERAR_STATUS_HORARIO_MARCADO = "ashm";
    public static final String PATH_LISTAR_SERVICO = "ls";
    public static final String PATH_LISTAR_SERVICO_AREA_ATUACAO = "lsaa";
    public static final String PATH_LISTAR_GRUPO_SERVICO_AREA_ATUACAO = "lgsaa";
    public static final String PATH_LISTAR_GRUPO_SERVICO_PRESTADO = "lgsp";
    public static final String PATH_DESASSOCIAR_FUNCIONARIO_SERVICO_PRESTADO_EMPRESA = "dfspe";
    public static final String PATH_LISTAR_FAVORITOS_CLIENTE = "lfc";
    public static final String PATH_CADASTRAR_FAVORITO_CLIENTE = "cfv";
    public static final String PATH_EXCLUIR_FAVORITO_CLIENTE = "efc";
    public static final String PATH_CADASTRO_HORARIO_MARCADO = "chm";
    public static final String PATH_LISTAR_HORARIO_MARCADO_PARA_EMPRESA = "lhmpe";
    public static final String PATH_LISTAR_HORARIO_MARCADO_PARA_CLIENTE = "lhmpc";
    //Codigo Falhas

    public final static String COD_FALHA_1035 = "1035";
    public final static String COD_FALHA_1036 = "1036";


    //Mensagens atenção
    public final static String M_AT_SEM_EMPRESA = "Infelizmente não existe nenhuma empresa cadastrada!";
    public static final String M_AT_SEM_CONEXAO = "Falha ao conectar com o servidor!";
    public static final String M_AT_NOME = "Ops, o campo do nome está vazio!";
    public static final String M_AT_DATA_NASCIMENTO = "Ops, o campo da data de nascimento está vazio!";
    public static final String M_AT_CPF = "Ops, o campo do CPF está vazio!";
    public static final String M_AT_USUARIO = "Ops, o campo do Usuario está vazio!";
    public static final String M_AT_SENHA = "Ops, o campo da Senha vazio!";

    public static final String POST = "POST";

    //Mensagem de informação
    public static final String M_INF_PERMISSAO_ACESSO_GALERIA = "Infelizmente sem permissao de acesso a galeria!";

    //Codigos de permissao
    public static final int COD_SOLICITACAO_PERMISSAO_GALERIA_CAMERA = 4001;
    public static final int COD_SOLICITACAO_PERMISSAO_LOCALIZACAO = 4002;

    //Codigo de solicitacao
    public static final int RC_SIGN_IN = 9001;
    public static final int COD_REQUEST_GALERIA_CLIENTE = 9002;
    public static final int COD_REQUEST_CAMERA_CLIENTE = 9003;
    public static final int COD_LOGIN_GOOGLE_CLIENTE = 9004;
    public static final int COD_LOGIN_GOOGLE_EMPRESA = 9005;
    public static final int COD_REQUEST_GALERIA_EMPRESA = 9006;
    public static final int COD_REQUEST_CAMERA_EMPRESA = 9007;
    public static final int COD_REQUEST_GALERIA_EMPRESA_FUNCIONARIO = 9008;
    public static final int COD_REQUEST_CAMERA_EMPRESA_FUNCIONARIO = 9009;

    //nome diretorio interno
    public static final String DIRETORIO_IMAGENS = "Picuma";

    //Caminhos de diretorio do app
    public static final String DIRETORIO_PRINCIPAL = "appPicuma/";
    public static final String CAMINHO_DIRETORIO_IMAGEM = DIRETORIO_PRINCIPAL + DIRETORIO_IMAGENS;

    //Dia da semana

    public static final String DIA_SEMANA_SEGUNDA = "Segunda-Feira";
    public static final String DIA_SEMANA_TERCA = "Terça-Feira";
    public static final String DIA_SEMANA_QUARTA = "Quarta-Feira";
    public static final String DIA_SEMANA_QUINTA = "Quinta-Feira";
    public static final String DIA_SEMANA_SEXTA = "Sexta-Feira";
    public static final String DIA_SEMANA_SABADO = "Sábado";
    public static final String DIA_SEMANA_DOMINGO = "Domingo";

    public static final int ID_DIA_SEMANA_SEGUNDA = 1;
    public static final int ID_DIA_SEMANA_TERCA = 2;
    public static final int ID_DIA_SEMANA_QUARTA = 3;
    public static final int ID_DIA_SEMANA_QUINTA = 4;
    public static final int ID_DIA_SEMANA_SEXTA = 5;
    public static final int ID_DIA_SEMANA_SABADO = 6;
    public static final int ID_DIA_SEMANA_DOMINGO = 7;

    //Horário marcado
    public static final String STATUS_CONFIRMADO = "C";
    public static final String STATUS_ABERTO = "A";
    public static final String STATUS_RECUSADO = "R";
    public static final String STATUS_SERVICO_REALIZADO = "S";

    //Mensagens

    public static final String M_CONTATO_VAZIO = "É necessário preencher o contato para prosseguir";
    public static final String M_AT_NOME_FUNCIONARIO_VAZIO = "Nome do funcionário vazio, por favor preencher!";
    public static final String M_INF_ENDERECO_N_ENCONTRADO = "Endereço não encontrado!";
    public static final String M_AT_DADOS_FORMULARIO = "Por favor preencher os campos obrigatórios!";
    public static final String M_AT_CADASTRO_CONTATO = "Necessário o cadastro de pelo menos um contato!";
    public static final String M_AT_HORARIO_GENERICO_CADASTRO = "É necessário o preenchimento do inicio e termino do expediente!";
    public static final String M_AT_HORARIO_INDIVIDUAL_CADASTRO = "É necessário o preenchimento do inicio e termino do expediente dos dias selecionados!";
    public static final String M_AT_DIA_HORARIO_INDIVIDUAL_CADASTRO = "É necessário a escolha de pelo menos um dia da semana para o cadastro de horário!";
    public static final String M_AT_DIA_HORARIO_GENERICO_CADASTRO = "É necessário atribuir o horário genérico a pelo menos um dia da semana!";
    public static final String M_AT_CADASTRO_ENDERECO = "Por favor preencher os campos obrigatórios!";
    public static final String M_AT_DIA_HORARIO_CADASTRO = "É necessário atribuir o horário a um dia da semana!";


    public static final String M_FALHA_AO_CADASTRAR = "Falha ao realizar o cadastro, por favor tente novamente!";

    public static final String FEMININO = "Feminino";
    public static final String MASCULINO = "Masculino";
    public static final String UNISSEX = "Unissex";

    //Banco de dados interno

    public static final String DB_NAME = "usuario";
    public static final int DB_VERSION = 1;
    public static final String DB_SQL_DROP_TABLE = "DROP TABLE IF EXIST usuario";
    public static final String DB_SQL_CREATE_TABLE_EMPRESA = "CREATE TABLE usuario (";

    public static final String TAG_FRAGMENT_MAPA_CLIENTE = "FragmentMapaCliente";
    public static final String TAG_CADASTRO_EMPRESA = "CadastroEmpresa";
    public static final String M_FALHA_RECUPERAR_LAT_LNG = "Falha ao recuperar Latitue & Longitute Api GEOCODE";
    public static final String M_ATENDIMENTO_PUBLICO_ESPECIFICO = "Atende apenas o público ";
    public static final String M_ATENDIMENTO_PUBLICO_UNISSEX = "Atendimento ";
    public static final String M_AT_SEM_EMPRESA_PRESTADORA_DE_SERVICO = "Infelizmente não tem nenhuma empresa prestadora deste serviço";
    public static final String M_FALHA_AO_RETIRAR_DE_FAVORITOS_EMPRESA = "Ocorreu uma falha ao retirar a empresa de seus favoritos.";
    public static final String M_FALHA_AO_ADICIONAR_EMPRESA_FAVORITOS = "Ocorreu uma falha ao adicionar a empresa aos seus favoritos.";
    public static final String M_CNPJ = "CNPJ: ";
    public static final String M_CPF = "CPF: ";
    public static final String M_AT_SEM_COMENTARIO_EMPRESA = "Infelizmente esta empresa não possui nenhum comentário!";
    public static final String M_AT_SEM_GRUPO_SERVICO_PARA_ESSA_AREA_ATUACAO = "Infelizmente não existe grupo de serviços cadastrado para está area de atuação!";
    public static final String M_AT_SEM_AREA_ATUACAO = "Infelizmente esta empresa não possui nenhum grupo de area de atuação";
    public static final String M_AT_SEM_FOTO_GALERIA = "Infelizmente esta empresa não possui nenhuma foto no album!";
    public static final String M_AT_SEM_GALERIA_EMPRESA = "Infelizmente esta empresa não possui nenhuma galeria de foto!";
    public static final String M_AT_SEM_GRUPO_SERVICO_PRESTADO = "Infelizmente esta empresa não possui nenhum grupo de serviço!";
    public static final String M_AT_SEM_SERVICO_PRESTADO_PARA_ESSE_GRUPO = "Infelizmente esta empresa não possui nenhum serviço!";
    public static final String M_AT_SEM_PROFISSIONAL_PARA_SERVICO_PRESTADO = "O serviço selecionado não tem nenhum profissional!";
    public static final String M_AT_SEM_FUNCIONARIO_EMPRESA = "Infelizmente esta empresa não possui nenhum funcionário!";
    public static final String M_FALHA_ALTERAR_NOME_ATENDIMENTO_EMPRESA = "Falha ao alterar nome e publico alvo da empresa!";
    public static final String M_FALHA_ALTERAR_DESCRICAO_EMPRESA = "Falha ao alterar descricao da empresa!";
    public static final String M_FALHA_ALTERAR_LOGO_EMPRESA = "Falha ao alterar logo da empresa!";
    public static final String M_FALHA_ALTERAR_ENDERECO_EMPRESA = "Falha ao alterar endereço da empresa!";
    public static final String M_FALHA_AO_CADASTRAR_CONTATO_EMPRESA = "Falha ao cadastrar um novo contato para a empresa!";
    public static final String M_FALHA_AO_EXCLUIR_CONTATO_EMPRESA = "Falha ao excluir contato da empresa!";
    public static final String M_AT_EXCLUSAO_CONTATO_EMPRESA = "É necessário pelo menos um contato cadastrado para empresa!";
    public static final String M_AT_EXCLUSAO_HORARIO_EMPRESA = "É necessário pelo menos um horário cadastrado para empresa!";
    public static final String M_FALHA_AO_EXCLUIR_HORARIO_EMPRESA = "Falha ao excluir horário da empresa!" ;
    public static final String M_FALHA_AO_CADASTRAR_HORARIO_EMPRESA = "Falha ao cadastrar um novo horário para a empresa!";
    public static final String M_FALHA_AO_CADASTRAR_FUNCIONARIO_EMPRESA = "Falha ao cadastrar funcionário(s) a empresa!";
    public static final String M_FALHA_ADICIONAR_IMG_FUNC = "Falha ao adicionar uma imagem ao funcionário!";
    public static final String M_FALHA_AO_EXCLUIR_FUNCIONARIO_EMPRESA = "Falha ao excluir funcionário!";
    public static final String M_AT_TEMPO_SERVICO_VAZIO = "É necessário o preenchimento do tempo aproximado de serviço!";
    public static final String M_AT_VALOR_SERVICO_VAZIO = "É necessário o preenchimento do valor minimo do serviço!";
    public static final String M_FALHA_AO_CADASTRAR_SERVICO_EMPRESA = "Falha ao cadastrar um serviço a empresa!";
    public static final String M_AT_FOTO_ANTES_VAZIO = "É necessário que seja selecionada uma foto de antes do serviço";
    public static final String M_AT_FOTO_DEPOIS_VAZIO = "É necessário que seja selecionada uma foto de depois do serviço";
    public static final String M_FALHA_AO_CADASTRAR_FOTO = "Falha ao adicionar novas fotos a galeria!";
    public static final String M_FALHA_AO_EXCLUIR_FOTO_EMPRESA = "Falha ao excluir foto!";
    public static final String M_FALHA_AO_RECUSAR_AGENDAMENTO = "Falha ao recusar agendamento!";
    public static final String TEXTO_STATUS_CONFIRMADO = "Agendamento confirmado!";
    public static final String TEXTO_STATUS_ABERTO = "Agendamento esperando confirmação!";
    public static final String TEXTO_STATUS_RECUSADO = "Agendamento recusado!";
    public static final String TEXTO_STATUS_REALIZADO_EMPRESA = "Serviço realizado!";
    public static final String TEXTO_STATUS_REALIZADO_CLIENTE = "Serviço realizado, deixe o seu comentário!";
    public static final String M_AT_HORARIO_AGENDADO_VAZIO = "Infelizmente não tem nenhum horário marcado!";
    public static final String M_FALHA_AO_ACESSAR_PERFIL_EMPRESA = "Falha ao acessar perfil empresa!";
    public static final String M_AT_USUARIO_SENHA_INVALIDO = "Usuario ou senha inválido!";
    public static final String NOME_ACTIVITY_AGENDAMENTO = "Agendamentos";
    public static final String NOME_ACTIVITY_FAVORITOS = "Favoritos";
    public static final String NOME_ACTIVITY_CADASTRO_CLIENTE = "Cadastro de cliente";
    public static final String NOME_ACTIVITY_CADASTRO_EMPRESA = "Cadastro de empresa";
    public static final String M_INF_CADASTRO_AGENDAMENTO_REALIZADO = "Cadastro de agendamento realizado com sucesso!";
    public static final String M_FALHA_AO_CADASTRAR_AGENDAMENTO = "Falha ao cadastrar agendamento!";
    public static final String M_FALHA_AO_ACEITAR_AGENDAMENTO = "Falha ao agendar horário!";
    public static final String M_COMENTARIO_CADASTRADO = "Comentário cadastrado com sucesso!";
    public static final String M_FALHA_AO_CADASTRAR_COMENTARIO = "Falha ao cadastrar comentário!";


    //
}
