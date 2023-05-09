using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class Profile
    {
        [Key]
        public int ProfileId { get; set; }

        [Required]
        public string Nume { get; set; }

        [Required]
        public string Prenume { get; set; }

        [Required]
        public string Adresa { get; set; }

        public string Fotografie { get; set; }

        public string UserId { get; set; }

        // Relatii
        [Required]
        public virtual ApplicationUser User { get; set; }
    }
}