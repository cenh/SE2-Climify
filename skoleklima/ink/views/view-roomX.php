<style>
    .view-roomX{
        height: 100%;
        width: 100%;
    }
    .wide-row{
        height: 400px;
        width: 100%;
        border-color: #00caf2;
        position: relative;
    }
    .my-center{
        margin: 0;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>

<div class="single-view view-roomX" ali>
    <div class="wide-row">
        <form id="temp" class="my-center">
            Value:<br>
            <input type="number" name="value">
        </form>
    </div>
    <div class="wide-row">
        <button id="my_button">
            my button
        </button>
    </div>

</div>
