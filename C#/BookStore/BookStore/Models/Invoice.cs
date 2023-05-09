using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Models
{
    public class Invoice
    {
        [Key]
        public int InvoiceId { get; set; }

        [Required]
        public DateTime Data { get; set; }

        [Required]
        public string AdresaFacturare { get; set; }

        [Required]
        public string AdresaLivrare { get; set; }

        // Relatii
        public virtual ICollection<Buy> Buys { get; set; }
    }
}