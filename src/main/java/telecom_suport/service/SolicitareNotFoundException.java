package telecom_suport.service;

public class SolicitareNotFoundException extends RuntimeException { public SolicitareNotFoundException(Long id) { super("Solicitarea nu exista: " + id); } }
