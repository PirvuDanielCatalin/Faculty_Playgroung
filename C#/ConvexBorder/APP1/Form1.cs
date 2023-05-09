using System;
using System.Drawing;
using System.IO;
using System.Text.RegularExpressions;
using System.Threading;
using System.Windows.Forms;
namespace APP1
{
    public partial class Window : System.Windows.Forms.Form
    {
        public class MyTuple
        {
            public MyTuple(int item1, int item2)
            {
                Item1 = item1;
                Item2 = item2;
            }
            public int Item1 { get; set; }
            public int Item2 { get; set; }
        }

        static Brush myBrush = new SolidBrush(Color.Green);
        static Brush FontBrush = new SolidBrush(Color.Black);
        Font myFont = new Font("Arial", 12);
        Pen myPen = new Pen(myBrush, 3);
        Graphics g = null;
        private int centerx, centery;

        string nr;
        string[] puncte;
        string ext;

        int n;
        Point[] Poly;
        Point exterior;
        MyTuple[] nodes;

        int times = 1;
        int finished1 = 0;
        int finished2 = 0;

        int Closest;
        int Right, Left;
        double value;
        int val = 1;

        public Window()
        {
            InitializeComponent();
            this.DoubleBuffered = true;
            centerx = canvas.Width / 5;
            centery = canvas.Height * 4 / 5;

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            Clear.Visible = false;
        }

        private double Distance(Point a, Point b)
        {
            return Math.Sqrt((a.X - b.X) * (a.X - b.X) + (a.Y - b.Y) * (a.Y - b.Y));
        }

        private float Orientation(Point a, Point b, Point c)
        {
            return ((b.X - a.X) * (c.Y - a.Y)) - ((b.Y - a.Y) * (c.X - a.X));

        }

        private void NodesScan()
        {
            nodes = new MyTuple[n + 1];
            for (int i = 0; i < n; i++)
            {
                if (i == 0)
                    nodes[i] = new MyTuple(n - 1, i + 1);
                else
                if (i == n - 1)
                    nodes[i] = new MyTuple(i - 1, 0);
                else
                    nodes[i] = new MyTuple(i - 1, i + 1);

            }
            nodes[n] = new MyTuple(-1, -1);
        }

        private void Comments()
        {
            if (times == 1)
            {
                char incr = 'A';
                Comentarii.Items.Add("Poligonul initial are " + nr + " puncte.\n");
                for (int i = 0; i < n; i++)
                {
                    Comentarii.Items.Add(incr + " ( " + ((Poly[i].X - centerx) / 16) + " , " + ((centery - Poly[i].Y) / 16) + " )\n");
                    incr++;
                }
                Comentarii.Items.Add("");
                Comentarii.Items.Add(incr + " ( " + ((exterior.X - centerx) / 16) + " , " + ((centery - exterior.Y) / 16) + " )\n");
                Comentarii.Items.Add("");
                incr = 'A';
                Comentarii.Items.Add("\nNodurile au fost parcurse in sens trigonometric");

                for (int i = 0; i < n; i++)
                {

                    if (i == 0)
                        ext = (char)(incr + i) + "\t" +
                        "St " + (char)(incr + n - 1) +
                        " ( " + ((Poly[n - 1].X - centerx) / 16) + " , " + ((centery - Poly[n - 1].Y) / 16) + " )\t" +
                        "Dr " + (char)(incr + i + 1) +
                        " ( " + ((Poly[i + 1].X - centerx) / 16) + " , " + ((centery - Poly[i + 1].Y) / 16) + " )\t";
                    else
                        if (i == n - 1)
                        ext = (char)(incr + i) + "\t" +
                        "St " + (char)(incr + i - 1) +
                        " ( " + ((Poly[i - 1].X - centerx) / 16) + " , " + ((centery - Poly[i - 1].Y) / 16) + " )\t" +
                        "Dr " + (char)(incr) +
                        " ( " + ((Poly[0].X - centerx) / 16) + " , " + ((centery - Poly[0].Y) / 16) + " )\t";
                    else
                        ext = (char)(incr + i) + "\t" +
                        "St " + (char)(incr + i - 1) +
                        " ( " + ((Poly[i - 1].X - centerx) / 16) + " , " + ((centery - Poly[i - 1].Y) / 16) + " )\t" +
                        "Dr " + (char)(incr + i + 1) +
                        " ( " + ((Poly[i + 1].X - centerx) / 16) + " , " + ((centery - Poly[i + 1].Y) / 16) + " )\t";

                    Comentarii.Items.Add(ext);
                    Comentarii.Items.Add("");

                }
            }

        }

        private void Citire_CheckedChanged(object sender, EventArgs e)
        {
            Clear.Visible = true;
            using (StreamReader RS = new StreamReader("date.txt"))
            {
                nr = RS.ReadLine();
                n = int.Parse(nr);

                puncte = new string[n];
                Poly = new Point[n];

                int coordx, coordy;
                string[] coords;

                for (int i = 0; i < n; i++)
                {
                    puncte[i] = RS.ReadLine();
                    coords = Regex.Split(puncte[i], @"\D+");
                    coordx = int.Parse(coords[0]) * 16 + centerx;
                    coordy = centery - int.Parse(coords[1]) * 16;
                    Poly[i] = new Point(coordx, coordy);
                }

                ext = RS.ReadLine();
                coords = Regex.Split(ext, @"\D+");
                coordx = int.Parse(coords[0]) * 16 + centerx;
                coordy = centery - int.Parse(coords[1]) * 16;
                exterior = new Point(coordx, coordy);

                RS.Close();
                Comments();
                NodesScan();
            }

        }

        private void DrawLetters()
        {
            char incr = 'A';
            for (int i = 0; i < Poly.Length; i++)
            {
                g.FillRectangle(FontBrush, Poly[i].X, Poly[i].Y, 5, 5);
                g.DrawString(incr + "", myFont, FontBrush, Poly[i].X, Poly[i].Y);
                incr++;
            }
            g.FillRectangle(FontBrush, exterior.X, exterior.Y, 5, 5);
            g.DrawString(incr + "", myFont, FontBrush, exterior);
        }

        private void Draw()
        {
            // g.FillEllipse(new SolidBrush(Color.Red),centerx,centery, 5, 5);

            if (Citire.Checked == true && Desen.Checked == true)
            {
                g.DrawPolygon(myPen, Poly);
                DrawLetters();
            }
            else
                canvas.Refresh();

        }

        private void Desen_CheckedChanged(object sender, EventArgs e)
        {
            Draw();

        }

        private void ClosestPoint()
        {
            if (Citire.Checked == true && Desen.Checked == true && Apropiat.Checked == true)
            {
                value = Distance(new Point((exterior.X - centerx) / 16, (centery - exterior.Y) / 16), new Point((Poly[0].X - centerx) / 16, (centery - Poly[0].Y) / 16));
                for (int i = 1; i < n; i++)
                {
                    double aux = Distance(new Point((exterior.X - centerx) / 16, (centery - exterior.Y) / 16), new Point((Poly[i].X - centerx) / 16, (centery - Poly[i].Y) / 16));
                    if (aux < value)
                    {
                        value = aux;
                        Closest = i;
                    }
                }
                char incr = (char)(65 + Closest);
                if (val == 1)
                {
                    Comentarii.Items.Add("");
                    Comentarii.Items.Add("Cel mai apropiat punct este " + (char)incr);
                    val++;
                }
                g.FillEllipse(new SolidBrush(Color.Red), Poly[Closest].X, Poly[Closest].Y, 5, 5);
                Left = Right = Closest;
            }
            else
                canvas.Refresh();

        }

        private void Apropiat_CheckedChanged(object sender, EventArgs e)
        {
            ClosestPoint();

        }

        private void Superioara(int param)
        {
            if (Citire.Checked == true && Desen.Checked == true && Apropiat.Checked == true && finished1 == 0)
            {
                g.DrawLine(new Pen(Color.Brown, 3), exterior, Poly[Right]);
                g.DrawLine(new Pen(Color.Brown, 3), Poly[Right], Poly[nodes[Right].Item2]);
                Thread.Sleep(700);

                g.DrawLine(new Pen(Color.Yellow, 3), exterior, Poly[nodes[Right].Item2]);
                Thread.Sleep(700);

                if (param == 1)
                {
                    g.DrawLine(myPen, exterior, Poly[nodes[Right].Item2]);
                    Thread.Sleep(700);

                    g.DrawLine(new Pen(canvas.BackColor, 3), exterior, Poly[Right]);
                    g.DrawLine(new Pen(canvas.BackColor, 3), Poly[Right], Poly[nodes[Right].Item2]);
                    DrawLetters();
                }
                else
                if (param == 2)
                {
                    g.DrawLine(new Pen(Color.Red, 3), exterior, Poly[nodes[Right].Item2]);
                    Thread.Sleep(700);

                    g.DrawLine(new Pen(canvas.BackColor, 3), exterior, Poly[nodes[Right].Item2]);
                    Thread.Sleep(700);

                    if (Right == Closest)
                        g.DrawLine(new Pen(canvas.BackColor, 3), exterior, Poly[Right]);
                    else
                        g.DrawLine(myPen, exterior, Poly[Right]);

                    g.DrawLine(myPen, Poly[Right], Poly[nodes[Right].Item2]);
                    DrawLetters();
                }
            }
        }

        private void FrontSup_CheckedChanged(object sender, EventArgs e)
        {
            if (finished1 == 0)
            {
                Point one = new Point((exterior.X - centerx) / 16, (centery - exterior.Y) / 16);
                Point two = new Point((Poly[Right].X - centerx) / 16, (centery - Poly[Right].Y) / 16);
                Point three = new Point((Poly[nodes[Right].Item2].X - centerx) / 16, (centery - Poly[nodes[Right].Item2].Y) / 16);

                if (Orientation(one, two, three) <= 0.0) //Viraj la dreapta
                {
                    Superioara(1);
                    nodes[n].Item2 = nodes[Right].Item2;
                    nodes[nodes[Right].Item2].Item1 = n;
                    Right = nodes[Right].Item2;

                }
                else //S-a gasit viraj la stanga deci aflarea noii frontiere convexe superioare s-a incheiat
                {
                    Superioara(2);
                    finished1 = 1;
                }

            }
        }

        private void Inferioara(int param)
        {
            if (Citire.Checked == true && Desen.Checked == true && Apropiat.Checked == true && finished2 == 0)
            {
                g.DrawLine(new Pen(Color.Brown, 3), exterior, Poly[Left]);
                g.DrawLine(new Pen(Color.Brown, 3), Poly[Left], Poly[nodes[Left].Item1]);
                Thread.Sleep(700);

                g.DrawLine(new Pen(Color.Yellow, 3), exterior, Poly[nodes[Left].Item1]);
                Thread.Sleep(700);

                if (param == 1)
                {
                    g.DrawLine(myPen, exterior, Poly[nodes[Left].Item1]);
                    Thread.Sleep(700);

                    g.DrawLine(new Pen(canvas.BackColor, 3), exterior, Poly[Left]);
                    g.DrawLine(new Pen(canvas.BackColor, 3), Poly[Left], Poly[nodes[Left].Item1]);
                    DrawLetters();
                }
                else
                {
                    g.DrawLine(new Pen(Color.Red, 3), exterior, Poly[nodes[Left].Item1]);
                    Thread.Sleep(700);

                    g.DrawLine(new Pen(canvas.BackColor, 3), exterior, Poly[nodes[Left].Item1]);
                    Thread.Sleep(700);

                    if (Left == Closest)
                        g.DrawLine(new Pen(canvas.BackColor, 3), exterior, Poly[Left]);
                    else
                        g.DrawLine(myPen, exterior, Poly[Left]);

                    g.DrawLine(myPen, Poly[Left], Poly[nodes[Left].Item1]);
                    DrawLetters();
                }
            }
        }

        private void FrontInf_CheckedChanged(object sender, EventArgs e)
        {
            if (finished2 == 0)
            {
                Point one = new Point((exterior.X - centerx) / 16, (centery - exterior.Y) / 16);
                Point two = new Point((Poly[Left].X - centerx) / 16, (centery - Poly[Left].Y) / 16);
                Point three = new Point((Poly[nodes[Left].Item1].X - centerx) / 16, (centery - Poly[nodes[Left].Item1].Y) / 16);

                if (Orientation(one, two, three) >= 0.0) //Viraj la stanga
                {
                    Inferioara(1);
                    nodes[n].Item1 = nodes[Left].Item1;
                    nodes[nodes[Left].Item1].Item2 = n;
                    Left = nodes[Left].Item1;
                }
                else //S-a gasit viraj la dreapta deci aflarea noii frontiere convexe inferioare s-a incheiat
                {
                    Inferioara(2);
                    finished2 = 1;
                    times = 2; //S-a determinat acoperirea convexa noua si rezultatul nou poate fi afisat
                }

            }
        }

        private void Result_Click(object sender, EventArgs e)
        {
            if (Citire.Checked == true && Apropiat.Checked == true)
            {
                char incr = 'A';
                if (times == 1)
                {
                    int start = Closest;
                    int final = start;
                    ext = "Before: " + (char)(incr + Closest);
                    start = nodes[start].Item2;

                    while (incr + start != incr + final)
                    {
                        ext += "" + (char)(incr + start);
                        start = nodes[start].Item2;

                    }
                    Comentarii.Items.Add("");
                    Comentarii.Items.Add(ext);
                    Comentarii.Items.Add("");
                }
                else
                if (times == 2)
                {
                    int start = n;
                    int final = start;
                    if (nodes[n].Item2 != -1)
                    {
                        ext = "After: " + (char)(incr + n) + "";
                        start = nodes[start].Item2;

                        while (incr + start != incr + final)
                        {
                            ext += "" + (char)(incr + start);
                            start = nodes[start].Item2;

                        }

                    }
                    else

                    {
                        ext = "Punctul " + (char)(incr + n) + " este in interiorul poligonului. =>Acoperirea convexa este poligonul initial.";
                    }
                    Comentarii.Items.Add("");
                    Comentarii.Items.Add(ext);
                    Comentarii.Items.Add("");

                }
            }
        }

        private void Clear_Click(object sender, EventArgs e)
        {
            Citire.Checked = false;
            Desen.Checked = false;
            Apropiat.Checked = false;
            FrontSup.Checked = false;
            FrontInf.Checked = false;
            g.Clear(Color.White);
            Comentarii.Items.Clear();
            times = 1;
            finished1 = 0;
            finished2 = 0;
        }

        private void Canvas_Paint(object sender, PaintEventArgs e)
        {
            g = canvas.CreateGraphics();
        }

    }
}
