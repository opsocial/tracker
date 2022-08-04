import { Usuario } from "./Usuario";

export interface Projeto {
  id_projeto: number,
  deck_arq_name: string,
  descricao: string,
  id_usuario: number,
  nome: string,
  segmento: string
}
