using CollectIt.Database.Entities.Account;
using CollectIt.Database.Infrastructure.Account.Data;
using CollectIt.MVC.Infrastructure;
using CollectIt.MVC.View.Controllers;
using CollectIt.MVC.View.ViewModels;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace CollectIt.Api.GraphQL.Controllers
{
    [ApiController]
    [Route("account")]
    public class AccountController : ControllerBase
    {
        private readonly SignInManager<User> _signInManager;
        private readonly UserManager _userManager;

        public AccountController(UserManager userManager,
                                 SignInManager<User> signInManager)
        {
            _userManager = userManager;
            _signInManager = signInManager;
        }

        [HttpPost]
        [Route("mobileLogin")]
        public async Task<IActionResult> MobileLogin([FromForm] LoginViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest();
            }
            try
            {

                var user = await _userManager.FindByEmailAsync(model.Email);
                if (user is null)
                {
                    ModelState.AddModelError("", "Пользователя с такой почтой не существует");
                    return BadRequest(model);
                }

                if (!(await _signInManager.PasswordSignInAsync(user, model.Password, model.RememberMe, false)).Succeeded)
                {
                    ModelState.AddModelError("", "Неправильный пароль");
                    return BadRequest(model);
                }

                await _signInManager.SignInAsync(user, model.RememberMe);
                return Ok(user.Id);
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            }
        }

        [HttpPost]
        [Route("mobileRegister")]
        public async Task<IActionResult> Register([FromForm] RegisterViewModel model)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest();
            }
            try
            {
                var user = new User { Email = model.Email, UserName = model.UserName };
                var result = await _userManager.CreateAsync(user, model.Password);
                if (result.Succeeded)
                {
                    return Ok(user.Id);
                }
                else
                    return BadRequest();
            }
            catch (Exception ex)
            {
                return BadRequest(ex);
            }
        }

    }
}