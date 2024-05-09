<!DOCTYPE html>
<html lang="es">
<head>
    <style>
        html {
            font-family: Helvetica, Arial, sans-serif;
            font-size: 11pt;
        }

        @page {
            margin: 120pt 32pt 150pt;
            size: A4 landscape;
        }

        @page {
            @top-center {
                content: element(header)
            }
        }

        @page {
            @bottom-center {
                content: element(footer)
            }
        }

        .footer {
            position: running(footer);
            display: block;
        }

        .header {
            position: running(header);
            text-align: right;
        }

        .linea {
            page-break-inside: avoid;
            width: 15%;
            display: inline-block;
        }

        .linea-header {
            display: inline-block;
            width: 22%;
        }

        .col-tabla {
            display: inline-block;
            width: 15%;
        }

        .letra-bold {
            font-weight: bold;
        }

    </style>
</head>
<body>

<div class="header">
    <div>
        <div style="float: left">O.S.M.E</div>
        <div><span class="letra-bold">Fecha de emisión</span>: $ {cabecera.fechaemision}</div>
    </div>
        <h2 style="text-align: center">Informe de item verificado</h2>
    <div style="margin: 0 auto 10px 40px; text-align: center; font-size: 11px;">
        <div style="display: inline"><span class="letra-bold">Comprobante</span>: $ {cabecera.tipoComprobante}</div>
        <div style="display: inline"><span class="letra-bold">Número</span>: $ {cabecera.nroComprobante}</div>
        <div style="display: inline"><span class="letra-bold">Fecha</span>: $ {cabecera.fechaEmision}</div>
        <div style="display: inline"><span class="letra-bold">Importe Total</span>: $ {cabecera.importeOriginal}</div>
        <div style="display: inline"><span class="letra-bold">Prestador</span>: $ {cabecera.prestador}</div>
    </div>
    <div style="margin: 0 auto;text-align: center; font-size: 11px;">
        <div class="linea-header"><span class="letra-bold">Entidad</span>: $ {cabecera.entidad}</div>
        <div class="linea-header"><span class="letra-bold">Denominación</span>: $ {cabecera.razonSocial}</div>
        <div class="linea-header"><span class="letra-bold">Plan</span>: $ {cabecera.plan}</div>
        <div class="linea-header"><span class="letra-bold">Afiliado N°</span>: $ {cabecera.numeroAfiliado}</div>
    </div>
    <br/>
    <div style="margin: 0 auto 15px auto;text-align: center; font-size: 11px; border-bottom: 1px solid black; border-top: 1px solid black; padding: 5px">
        <div class="col-tabla"><span class="letra-bold">Concepto Anotación</span></div>
        <div class="col-tabla"><span class="letra-bold">Nombre</span></div>
        <div class="col-tabla"><span class="letra-bold">Total Débito</span></div>
        <div class="col-tabla"><span class="letra-bold">Fecha Prestación</span></div>
        <div class="col-tabla"><span class="letra-bold">Descripción Item</span></div>
    </div>

</div>

<div class="footer">
    <p style="border-bottom: 1px solid black; border-top: 1px solid black; padding: 5px; font-size: 11px">
        <span class="letra-bold">Debito por Dif</span>
        :
    </p>
    <div style="display: inline-block; width: 30%; font-size: 11px">Total debitado: $ {totalDebitado}</div>
    <div style="display: inline-block; width: 65%; font-size: 11px">Total a pagar comprobante: $ {totalPagar}</div>
</div>

<#list detalles as detalle>
    <div style="padding: 2px; margin-top: 5px; text-align: center; font-size: 11px;">
        <div class="linea">$ {detalle.conceptoPrestacional}</div>
        <div class="linea">$ {detalle.nombreAfiliado}</div>
        <div class="linea">$ {detalle.importeDebito}</div>
        <div class="linea">$ {detalle.fechaPresentacion}</div>
        <div class="linea">$ {detalle.descripcionDebito}</div>
    </div>
</#list>

</body>
</html>
