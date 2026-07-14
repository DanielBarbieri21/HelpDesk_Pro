export interface Chamado {
  id?: number;
  titulo: string;
  descricao: string;
  prioridade: 'BAIXA' | 'MEDIA' | 'ALTA' | 'URGENTE';
  status: 'ABERTO' | 'EM_ANDAMENTO' | 'AGUARDANDO_CLIENTE' | 'RESOLVIDO' | 'FECHADO';
  clienteId?: number;
  tecnicoId?: number;
  dataAbertura?: string;
  dataAtualizacao?: string;
  dataFechamento?: string;
  slaExpirado?: boolean;
}
