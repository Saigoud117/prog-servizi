package EsportazioneMitProg;

public class Validate
{
    private String tipo;

    private String messaggio;

    private String nome;

    public String getTipo ()
    {
        return tipo;
    }

    public void setTipo (String tipo)
    {
        this.tipo = tipo;
    }

    public String getMessaggio ()
    {
        return messaggio;
    }

    public void setMessaggio (String messaggio)
    {
        this.messaggio = messaggio;
    }

    public String getNome ()
    {
        return nome;
    }

    public void setNome (String nome)
    {
        this.nome = nome;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tipo = "+tipo+", messaggio = "+messaggio+", nome = "+nome+"]";
    }
}
