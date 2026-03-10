# Projeto LPV

Instruções para compilar e executar o projeto localmente na sua máquina.

## Como executar

1. Faça o download ou clone o projeto para o seu computador.
2. Abra o **Terminal** (ou Prompt de Comando).
3. Navegue até a pasta raiz do projeto.
4. Execute os comandos abaixo sequencialmente para compilar e rodar a aplicação:

```bash
# 1. Compila os arquivos .java da pasta src para a pasta bin
javac -d bin src\*.java

# 2. Executa a classe principal do projeto
java -cp bin Main
