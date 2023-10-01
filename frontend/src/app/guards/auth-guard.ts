import { TokenService } from './../authentication/token.service';
import { inject } from "@angular/core"
import { Router } from "@angular/router"

export const authGuard = () => {
  const tokenService = inject(TokenService);
  const router = inject(Router);

  if(tokenService.existsToken()) {
    return true;
  } else {
    alert("Realize login para acessar a funcionalidade.");
    router.navigate(['/authenticate']);
    return false;
  }
}
