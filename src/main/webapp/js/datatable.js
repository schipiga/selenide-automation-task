YUI().use('datatable', 'datatype-number', 'datasource', function (Y) {


    // create datatable

    function formatNumber(cell) {
        valueAsNumber = Y.DataType.Number.parse(cell.value);
        return Y.DataType.Number.format(valueAsNumber, {
            decimalSeparator: ",",
            decimalPlaces: 2
        });
    }

    // define a datasource
    var myDataSource = new Y.DataSource.Get({
        source: "/knowit/rest/university/results?"
    });

    // define a datasource schema

    myDataSource.plug({fn: Y.Plugin.DataSourceJSONSchema, cfg: {
        schema: {
            resultListLocator: "students",
            resultFields: [
                { key: "id", parser: "number" },
                { key: "fullname", parser: "string" },
                { key: "discipline1", parser: "number" },
                { key: "discipline2", parser: "number" },
                { key: "discipline3", parser: "number" },
                { key: "weighted", parser: "number" }
            ]
        }
    }});

    var table = new Y.DataTable({
        caption: "Knowit university achievements list",
        columns: [
            { key: "id", label: "Id" },
            { key: "fullname", label: "Full name" },
            { key: "discipline1", label: "Java" },
            { key: "discipline2", label: "OOD" },
            { key: "discipline3", label: "Webapps" },
            { key: "weighted", label: "Weighted Average", formatter: formatNumber }],
        sortable: true
    });


    table.plug(Y.Plugin.DataTableDataSource, {
        datasource: myDataSource
    });

    table.datasource.load({
        request: encodeURIComponent('')
    });

    // tell YUI to render the table
    table.render("students");
});