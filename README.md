# 🎫 GeniusXP

![Capa](.github/cover.png)

## 💡 Explicação do projeto
GeniusXP é uma plataforma centralizada para imersão em eventos que simplifica operações como inscrições, pagamentos e check-in, enquanto aumenta o engajamento com enquetes e networking. A inteligência artificial da GeniusXP utiliza as preferências dos usuários para oferecer uma experiência altamente personalizada e otimizar o planejamento. Com análise de sentimento e assistência virtual, a plataforma proporciona interações mais significativas, elevando a eficiência da gestão e tornando os eventos mais impactantes para cada participante.

## 🎥 Pré-visualização do projeto
https://github.com/user-attachments/assets/077a2976-79ec-4e86-8f37-f0a2e3b020ec

https://github.com/user-attachments/assets/4be8c8b5-5bbd-4c9e-9488-44967d833b59

## 📱 Telas desenvolvidas
- [Boas vindas](./app/src/main/res/layout/activity_welcome.xml)
- [Login](./app/src/main/res/layout/activity_signin.xml)
- [Cadastro de conta](./app/src/main/res/layout/activity_signup.xml)
- [Recuperação de senha](./app/src/main/res/layout/activity_recover_password.xml)
- [Listagem de ingressos](./app/src/main/res/layout/fragment_tickets.xml)
- [Perfil](./app/src/main/res/layout/fragment_profile.xml)
- [Detalhes do ingresso](./app/src/main/res/layout/activity_ticket_details.xml)
- [Listagem de palestrantes](./app/src/main/res/layout/fragment_speakers.xml)
- [Cronograma do evento](./app/src/main/res/layout/fragment_schedule.xml)
- [Página de Interação](./app/src/main/res/layout/fragment_interaction.xml)
- [Mapa do evento](./app/src/main/res/layout/fragment_map.xml)

## 🎲 Dados salvos em armazenamento local (Async Storage)
Atualmente, os dados de perfil do usuário e token de acesso são salvos utilizando Shared Preferences no arquivo [Profile.kt](./app/src/main/java/com/github/ericknathan/geniusxp/services/Profile.kt#L17)

## 🔗 Integrações
- [[POST] Login](./app/src/main/java/com/github/ericknathan/geniusxp/ui/activity/SignInActivity.kt#L70)
- [[POST] Cadastro de usuário](./app/src/main/java/com/github/ericknathan/geniusxp/ui/activity/SignUpActivity.kt#L104)
- [[PUT] Recuperação de senha](./app/src/main/java/com/github/ericknathan/geniusxp/ui/activity/RecoverPasswordActivity.kt#L46)
- [[GET] Listagem de ingressos](./app/src/main/java/com/github/ericknathan/geniusxp/ui/fragments/TicketsFragment.kt#L35)
- [[GET] Perfil](./app/src/main/java/com/github/ericknathan/geniusxp/services/Profile.kt#L25)
- [[GET] Listagem de palestrantes](./app/src/main/java/com/github/ericknathan/geniusxp/ui/fragments/SpeakersFragment.kt#L45)
- [[GET] Listagem de dias de evento](./app/src/main/java/com/github/ericknathan/geniusxp/ui/fragments/ScheduleFragment.kt#L47)
- [[GET] Listagem de palestras](./app/src/main/java/com/github/ericknathan/geniusxp/ui/fragments/ScheduleFragment.kt#L92)
- [[POST] Envio de mensagem no chat](./app/src/main/java/com/github/ericknathan/geniusxp/ui/fragments/InteractionFragment.kt#L73)
- [[DELETE] Deslogar usuário](./app/src/main/java/com/github/ericknathan/geniusxp/ui/fragments/ProfileFragment.kt#L95)

## 📂 Estrutura de pastas
A estrutura do projeto GeniusXP foi organizada de forma a garantir modularidade e escalabilidade. Cada parte da aplicação foi separada em pacotes e pastas específicas, facilitando a manutenção e o entendimento do código. Abaixo, está descrita a organização dos principais diretórios e seus respectivos papéis na arquitetura do sistema, abordando tanto o código de back-end quanto os recursos de interface visual.

![GeniusXP - Diagrama de pastas](https://github.com/user-attachments/assets/d2e7a244-8c53-4d13-847e-77aaec3986ec)

- app/src/main/java/com/github/ericknathan/geniusxp - Pacote principal (Kotlin)
    - `enums`: Contém os enumeradores que definem constantes e categorias específicas usadas em todo o projeto, como status de eventos, tipos de ingressos, etc.
    - `models`: Pasta destinada aos modelos de dados, responsáveis pela estruturação dos dados que trafegam entre a API, o banco de dados e a interface do usuário. Exemplos incluem classes para usuários, eventos, ingressos e palestrantes.
    - `services`: Agrupa as classes que lidam com a lógica de negócios e a comunicação com APIs externas. Os serviços aqui implementam funcionalidades como envio de notificações, autenticação de usuário, e gerenciamento de eventos e inscrições.
    - `ui`: Contém as classes responsáveis pela lógica de interação com o usuário. Isso inclui activities e fragments que controlam o fluxo da interface, como login, cadastro, e navegação entre seções do aplicativo.
    - `utils`: Classes utilitárias que fornecem funções auxiliares, como formatação de datas, manipulação de strings e outras operações repetitivas, facilitando o reuso de código.
- app/src/main/res - Recursos do aplictivo (XML)
    - `drawable`: Contém os recursos gráficos como ícones, botões, e imagens usadas na interface do aplicativo.
    - `font`: Armazena fontes personalizadas que podem ser usadas no design da interface, garantindo uma identidade visual única.
    - `layout`: Pasta que contém os arquivos XML responsáveis pela definição das telas e componentes visuais do aplicativo, como activities e fragments.
    - `menu`: Contém arquivos XML que definem os menus da aplicação, como menus de navegação ou de contexto em telas específicas.
    - `mipmap`: Pasta usada para armazenar ícones de aplicativo em diferentes resoluções para suportar dispositivos com diferentes densidades de tela.
    - `values`: Armazena arquivos XML que definem recursos reutilizáveis, como strings, cores, dimensões e estilos aplicados em toda a aplicação.
    - `values-night`: Contém as definições de cores, estilos e temas específicas para o modo escuro da interface.
    - `values-pt-rBR`: Arquivo que contém as traduções e strings localizadas em português brasileiro, permitindo que o aplicativo suporte múltiplos idiomas.

## 👥 Equipe
Este projeto está sendo desenvolvido pelos seguintes membros:

- RM99565 - Erick Nathan Capito Pereira (2TDSPV)
- RM99577 - Guilherme Dias Gomes (2TDSPX)
- RM550889 - Hemily Nara da Silva (2TDSPX)
- RM550841 - Lucas Araujo Oliveira Silva (2TDSPV)
- RM99409 - Michael José Bernardi Da Silva (2TDSPX)
