using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Models
{
    public class Book
    {
        [Key]
        public int BookId { get; set; }

        [Required(ErrorMessage = "Campul este obligatoriu!")]
        public string Titlu { get; set; }

        [Required(ErrorMessage = "Campul este obligatoriu!")]
        public string Autor { get; set; }

        [Required(ErrorMessage = "Campul este obligatoriu!")]
        public string Editura { get; set; }

        [Required(ErrorMessage = "Campul este obligatoriu!")]
        [MinLength(20, ErrorMessage = "Descrierea trebuie sa contina minim 20 caractere!")]
        public string Descriere { get; set; }

        public string Fotografie { get; set; }

        [Required(ErrorMessage = "Campul este obligatoriu!")]
        public int Pret { get; set; }

        public int Status { get; set; } // 1 = In Shop; 0 = Created by a partner

        // Relatii
        public virtual ICollection<BookCategory> BookCategories { get; set; }
        public virtual ICollection<BookComment> BookComments { get; set; }
        public virtual ICollection<Rating> Ratings { get; set; }
        public virtual ICollection<Buy> Buys { get; set; }
        public virtual ICollection<PartnerRequirement> PartnerRequirements { get; set; }
        public virtual Stock Stock { get; set; }
    }
}