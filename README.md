
# Funcionalitats

Aquesta llibreria té les funcionalitats de controlar els loadings a les vistes, la gestió de missatges (notificacions, errors...) i la navegació de l'app relacionada amb alguns tipus d'errors.

# Instruccions per utilitzar la llibreria

Primer de tot hem d'importar la llibreria al projecte a **build.gradle(:app)**

    implementation 'es.app2u.beat:viewutils:1.1.0'

## Afegir loading a la view

1. Lo normal serà declarar la variable al ViewModel de la vista per poder mostrar-lo després. Internament, la variable notifier serà un liveData, així que podrem afegir després un observable a la vista per controlar quan mostrar-la.

        private val notifier: PopupNotifier = PopupNotifier()
        fun getNotifier(): PopupNotifier = notifier

2. Per poder gestionar el loading, podem fer un **startLoading()** i un **cancelLoading()** al moment que dessitjem. 

*Exemple: amb una funció login.*
    
    fun actionLogin(username: String, password: String) {
        loginUC.execute(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {

                override fun onSubscribe(d: Disposable) {
                    notifier.startLoading()
                }

                override fun onComplete() {
                    notifier.cancelLoading()
                    navigationLD.value = true
                }

                override fun onError(error: Throwable) {
                    notifier.cancelLoading()
                    notifier.notifyServerErrors(error, ErrorManager)
                }
            })
    }

3. Per observar els canvis a la variable i poder mostrar un loading quan es necessiti, declarem la variable a la vista. Internament ja fa el observable, així que no tindrem que fer res més per mostrar-la a la vista. 
Quan el ViewModel cride a **notifier.startLoading()** es mostrarà i quan cride a **cancelLoading()** s'amagarà.
    
        // Loading
        PopupDispatcher(requireContext(), this, vm.getNotifier())


## Sobrescriure la navegació abans de sobreescriure els errors

Es **obligatori** fer això si vols sobrescriure els errors. *Secció següent.*

Es controla perque al haver un error Unauthorized (401) a qualsevol lloc de l'app, es navegue cap al login.

1. A la classe que controla la navegació, s'ha de implemetar la interficie PopupDispatcher.Configuration.Navigation. 

*Exemple:*

    class MainActivity : AppCompatActivity(), PopupDispatcher.Configuration.Navigation {

2. Declarem la variable al onCreate().

        PopupDispatcher.configure(PopupDispatcher.Configuration(this))

3. S'ha d'implementar les funcions de logout() i goBack(). En aquest cas, anem a sobreescriure logout() i a goBack() no fem res. 

*Exemple:*

    override fun logout() {
        vm.logout()
    }

    override fun goBack() {
        // Do nothing
    }



## Com sobrescriure els errors i canviar el comportament per defecte

1. Crear classe que hereta de ErrorsManager.
	
*Exemple:*

	class NomProjecteErrorsManager: ErrorsManager() {}

2. A la classe AppDelegate (clase que hereta de Application()), declarar la nova clase creada en el pas anterior.

*Exemple:*

    override fun onCreate() {
        super.onCreate()
        PopupNotifier.setConfiguration(PopupNotifier.Configuration(IngescoErrorsManager))
    }

3. Sobrescriure la funció manageServerErrors(e: Throwable): Message {}
	
*Exemple: per sobresciure que fer en l'error 401. Pots fer una classe apart que heredi de la del pas 1 si vols.*

	override fun manageServerErrors(e: Throwable): Message {
	    return if (e is ResponseError) {
	        manageLoginErrors(e)
	    }
	    else {
	        super.manageServerErrors(e)
	    }
	}

	private fun manageLoginErrors(e: ResponseError): Message {
        return when (e.errorCode) {
            401 -> ErrorDialogMessage(R.string.errorAuthentication)
            else -> super.manageServerErrors(e)
        }
    }

4. Si només volem sobreescriure els missatges, podem fer override a les funcions directament.

*Exemple:*

	// Message connection error
    override fun manageConnectionError(error: ConnectionError?): Message {
        return DialogMessage(this.defaultTitle, Msg(R.string.errorConnection))
    }

	// Message unauthorized error
    override fun getUnauthorizedMessage(): Msg {
        return Msg(R.string.errorAuthentication)
    }

}

5. Si volem traduir un tipus d'error que no sigui genèric (com Connection o Unauthorized):

*Exemple: per traduir un error 409.*

	private object KeyErrors {
        const val CONFLICT_USER = "user_email_already_used"
    }

    // Error translations
    private val errorMessages: Map<String, Msg> = mapOf(KeyErrors.CONFLICT_USER to Msg(R.string.errorUserAlreadyExists))

*Exemple: En un subscribe d'un registerUC al ViewModel, controlem l'error en el onError(error: Throwable) cridant a errorMessages.*

    .subscribe(object : CompletableObserver {

        override fun onSubscribe(d: Disposable) {
            notifier.startLoading()
        }

        override fun onComplete() {
            notifier.cancelLoading()
            navigationLD.value = true
        }

        override fun onError(error: Throwable) {
            notifier.cancelLoading()
            when (error) {
                is InvalidEmailException -> notifier.notifyMessage(ErrorDialogMessage(Msg(R.string.registerValidEmail)))
                else -> notifier.notifyServerErrors(error, errorMessages)
            }
        }
    })
