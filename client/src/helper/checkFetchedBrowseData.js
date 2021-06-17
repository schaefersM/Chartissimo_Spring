import { checkDayData, checkHourData, labelGenerator, randomColor } from "./";

/**
 *
 * Prüft, ob die Datensätze unvollständig sind.
 * Generiert ggf. neue X- und Y-Achsen.
 * Generiert Optionen für den Datensatz, die u.a. den Namen und ggf. die generierte Graphenfarbe enthält
 * @returns labels: die neuen Labels (x-Achse)
 * @returns datasets: die überprüften Datensätze
 * @returns options: die neuen Optionen
 * @returns colorId: die generierte Graphenfarbe
 */

export default function checkFetchedBrowseData(
	data,
	config,
	type,
	host,
	date,
	fetchHour,
	reset = false
) {
	let hour = fetchHour < 10 ? `0${fetchHour}` : `${fetchHour}`;
	let validData;
	let labels;
	if (data[0][0] && typeof data[0][0].hour === "number") {
		validData = data.map((item) => checkDayData(item));
		labels = labelGenerator("hour", fetchHour, reset);
	} else if (data[0][0] && typeof data[0][0].minute === "number") {
		validData = data.map((item) => checkHourData(item));
		labels = reset
			? labelGenerator("minute", fetchHour, reset)
			: labelGenerator("minute", fetchHour, reset, config.hours);
	} else {
		console.log("invalid Data");
	}

	//TODO Datasetslabel in einer Funktion für Browser and ChartPage Komponent
	const datasetLabel =
		hour !== "25" ? `${host}-${date}-${hour}:00` : `${host}-${date}`;
	const datasetTemp = {
		label: `temperature-${datasetLabel}`,
		yAxisID: "temperature",
	};
	const datasetHum = {
		label: `humidity-${datasetLabel}`,
		yAxisID: "humidity",
	};
	const datasetOptions = {
		fill: false,
		pointRadius: 3,
	};
	let newDatasetTemp;
	let newDatasetHum;
	let newOptions = { ...config.options };
	switch (type) {
		case "comparison":
			newDatasetTemp = { ...datasetTemp, ...datasetOptions };
			newDatasetTemp.backgroundColor = newDatasetTemp.borderColor =
				"#f44242";
			newDatasetTemp.data = validData[0];
			newDatasetHum = { ...datasetHum, ...datasetOptions };
			newDatasetHum.backgroundColor = newDatasetHum.borderColor =
				"#6242f4";
			newDatasetHum.data = validData[1];
			newOptions = {
				...newOptions,
				scales: {
					xAxes: [...newOptions.scales.xAxes],
					yAxes: [
						{
							...newOptions.scales.yAxes[0],
							display: true,
							scaleLabel: {
								...newOptions.scales.yAxes[0].scaleLabel,
								fontColor: "#f44242",
							},
						},
						{
							...newOptions.scales.yAxes[1],
							display: true,
							scaleLabel: {
								...newOptions.scales.yAxes[1].scaleLabel,
								fontColor: "#6242f4",
							},
						},
					],
				},
			};
			return {
				labels,
				datasets: [newDatasetTemp, newDatasetHum],
				colorId: ["#f44242", "#6242f4"],
				options: newOptions,
			};
		case "temperature":
			const tempColor = randomColor(config.colorIds);
			newDatasetTemp = { ...datasetTemp, ...datasetOptions };
			newDatasetTemp.backgroundColor = newDatasetTemp.borderColor = tempColor;
			newDatasetTemp.data = validData[0];
			return {
				labels,
				datasets: [newDatasetTemp],
				colorId: [tempColor],
				options: newOptions,
			};
		case "humidity":
			const humColor = randomColor(config.colorIds);
			newDatasetHum = { ...datasetHum, ...datasetOptions };
			newDatasetHum.backgroundColor = newDatasetHum.borderColor = humColor;
			newDatasetHum.data = validData[0];
			return {
				labels,
				datasets: [newDatasetHum],
				colorId: [humColor],
				options: newOptions,
			};
		default:
			break;
	}
}
